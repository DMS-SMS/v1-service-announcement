package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.ViewRepository
import org.springframework.stereotype.Component

@Component
class CheckAnnouncementUseCaseImpl(
        val announcementRepository: AnnouncementRepository,
        val viewRepository: ViewRepository
): CheckAnnouncementUseCase {
    override fun execute(uuid: String, type: String): Int {
        val announcements = announcementRepository.findAllByType(type)

        for (announcement: Announcement in announcements) {
            viewRepository.findByUuid(announcement.uuid)?.let {
                it.read_accounts.find { it == uuid }?.let {
                    return 1
                }
            }
        }
        return 0
    }
}