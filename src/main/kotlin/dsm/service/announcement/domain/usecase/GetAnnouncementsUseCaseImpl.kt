package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.StudentRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsUseCaseImpl(
        val announcementRepository: AnnouncementRepository,
        val studentRepository: StudentRepository
): GetAnnouncementsUseCase {
    override fun execute(accountUuid: String, type: String, start: Int, count: Int): MutableIterable<Announcement> {
        if (type == "club") {
            return announcementRepository.findByType(type, PageRequest.of(start,count))
        } else {
            studentRepository.findByUuid(accountUuid)?.let {
                if (it.grade == 0) return announcementRepository.findByType("school", PageRequest.of(start,count))
                return announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContains(
                        "school", it.grade.toString(), it.group.toString(), PageRequest.of(start, count))
            }

        }
        return announcementRepository.findByType("school", PageRequest.of(start,count))
    }
}