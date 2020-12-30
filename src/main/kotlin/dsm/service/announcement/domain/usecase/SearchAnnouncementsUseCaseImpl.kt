package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.exception.BadRequestException
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.StudentRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class SearchAnnouncementsUseCaseImpl(
        val announcementRepository: AnnouncementRepository,
        val studentRepository: StudentRepository

): SearchAnnouncementsUseCase {
    override fun execute(accountUuid: String, type: String, query: String, start: Int, count: Int):
            Pair<MutableIterable<Announcement>, Long> {
        if (type.equals("club")) {
            return Pair(announcementRepository.findByTitleContainsAndTypeOrderByDateDesc(query, type, PageRequest.of(start,count)),
                    announcementRepository.countByTitleContainsAndType(query, type))
        } else if (type.equals("school")) {
            studentRepository.findByUuid(accountUuid)?.let {
                return Pair(announcementRepository.findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                        query, "school", it.grade.toString(), it.group.toString(), PageRequest.of(start, count)),
                        announcementRepository.countByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContains(
                                query, "school", it.grade.toString(), it.group.toString()
                        ))
            }
            return Pair(announcementRepository.findByTitleContainsAndTypeOrderByDateDesc(query, type, PageRequest.of(start,count)),
                    announcementRepository.countByTitleContainsAndType(query, type))
        } else {
            throw BadRequestException();
        }
    }
}