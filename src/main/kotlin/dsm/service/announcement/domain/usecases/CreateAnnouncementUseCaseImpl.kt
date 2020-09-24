package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.infrastructure.util.RandomKey
import org.json.JSONObject

class CreateAnnouncementUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): CreateAnnouncementUseCase {
    override fun createAnnouncementUseCase(announcement: Announcement) {
        announcement.uuid = createAnnouncementUuidUseCase()
        announcementRepository.save(announcement)
    }

    override fun createAnnouncementUuidUseCase(): String {
        while (true) {
            val key = RandomKey().generateRandomKey(12)
            if (announcementRepository.findByUuid(key) == null )
                return "Announcement-$key"
        }
    }

    override fun createContentUuidUseCase(): String {
        while (true) {
            val key = RandomKey().generateRandomKey(12)
            if (announcementRepository.findContentByUuid(key) == null )
                return key
        }
    }
}