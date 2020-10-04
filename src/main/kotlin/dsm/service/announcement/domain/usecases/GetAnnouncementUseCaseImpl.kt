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

    override fun getAnnouncement(announcementUuid: String): Announcement? {
        return announcementRepository.findByUuid(announcementUuid)
    }

    override fun getAnnouncementContent(contentUuid: String): Document? {
        return announcementRepository.findContentByUuid(contentUuid)
    }
}