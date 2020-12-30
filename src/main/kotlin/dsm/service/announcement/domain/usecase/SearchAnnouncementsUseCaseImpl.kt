package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
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
        if (type == "club") {
            return Pair(announcementRepository.findByTitleContainsAndTypeOrderByDateDesc(query, type, PageRequest.of(start,count)),
                    announcementRepository.countByTitleContainsAndType(query, type))
        } else {
            studentRepository.findByUuid(accountUuid)?.let {
                val garbage = Pair(announcementRepository.findByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContains(
                        query, "school", it.grade.toString(), it.group.toString(), PageRequest.of(start, count)),
                        announcementRepository.countByTitleContainsAndTypeAndTargetGradeContainsAndTargetGroupContains(
                                query, "school", it.grade.toString(), it.group.toString()
                        ))
                print(garbage)
                print("asdf")
                return garbage
            }
            return Pair(announcementRepository.findByTitleContainsAndTypeOrderByDateDesc(query, type, PageRequest.of(start,count)),
                    announcementRepository.countByTitleContainsAndType(query, type))
        }
    }
}