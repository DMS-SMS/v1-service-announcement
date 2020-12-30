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
        println("aasdf")
        if (type.equals("club")) {
            println("club")
            return Pair(announcementRepository.findByTitleContainsAndTypeOrderByDateDesc(query, type, PageRequest.of(start,count)),
                    announcementRepository.countByTitleContainsAndType(query, type))
        } else if (type.equals("school")) {
            println("school")
            studentRepository.findByUuid(accountUuid)?.let {
                println("sss")
                return Pair(announcementRepository.findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContains(
                        query, "school", it.grade.toString(), it.group.toString(), PageRequest.of(start, count)),
                        announcementRepository.countByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContains(
                                query, "school", it.grade.toString(), it.group.toString()
                        ))
            }
            println("aaa")
            return Pair(announcementRepository.findByTitleContainsAndTypeOrderByDateDesc(query, type, PageRequest.of(start,count)),
                    announcementRepository.countByTitleContainsAndType(query, type))
        } else {
            throw BadRequestException();
        }
    }
}