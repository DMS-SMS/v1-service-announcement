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
        return if (type == "club") {
            announcementRepository.findAllByType(type, PageRequest.of(start,count))
        } else {
            studentRepository.findByUuid(accountUuid)?.let {
                announcementRepository.findAllByTypeAndTargetGradeAndTargetGroup(
                        "school", it.grade, it.group, PageRequest.of(start, count))
            }
            announcementRepository.findAllByType(type, PageRequest.of(start,count))
        }
    }
}