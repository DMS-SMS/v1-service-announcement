package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import org.json.JSONObject

class CreateAnnouncementUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): CreateAnnouncementUseCase {
    override fun createAnnouncementUseCase(announcement: Announcement) {
        announcement.uuid = createAnnouncementUuidUseCase()
        announcementRepository.save(announcement)
    }

    override fun createAnnouncementUuidUseCase(): String {
        return "TemporaryAnnouncementUuid"
    }

    override fun createContent(content: String): String {
        println(content)
        return announcementRepository.saveContent(content)
    }
}