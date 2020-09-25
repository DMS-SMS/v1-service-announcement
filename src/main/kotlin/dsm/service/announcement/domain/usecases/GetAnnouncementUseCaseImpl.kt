package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository

class GetAnnouncementUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): GetAnnouncementUseCase {
    override fun getAnnouncement(uuid: String, type: String): List<Announcement?> {
        return if (type == "club") {
            announcementRepository.findClubAnnouncements()
        } else {
            announcementRepository.findSchoolAnnouncements(uuid)
        }
    }
}