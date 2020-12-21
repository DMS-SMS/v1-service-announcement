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
    override fun execute(accountUuid: String, type: String, query: String, start: Int, count: Int): MutableIterable<Announcement> {
        return if (type == "club") {
            announcementRepository.findByTitleContainsAndType(query, type, PageRequest.of(start,count))
        } else {
            studentRepository.findByUuid(accountUuid)?.let {
                announcementRepository.findByTitleContainsAndTypeAndTargetGradeContainingAndTargetGroupContaining(
                        query, "school", it.grade.toString(), it.group.toString(), PageRequest.of(start, count))
            }
            announcementRepository.findByTitleContainsAndType(query, type, PageRequest.of(start,count))
        }
    }
}