package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import org.bson.Document

class GetAnnouncementUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): GetAnnouncementUseCase {
    override fun getAnnouncements(uuid: String, type: String): List<Announcement?> {
        return if (type == "club") {
            announcementRepository.findClubAnnouncements()
        } else {
            announcementRepository.findSchoolAnnouncements(uuid)
        }
    }

    override fun getAnnouncement(uuid: String): Announcement? {
        return announcementRepository.findByUuid(uuid)
    }

    override fun getAnnouncementContent(uuid: String): Document? {
        return announcementRepository.findContentByUuid(uuid)
    }
}