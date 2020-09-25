package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository

class GetAnnouncementUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): GetAnnouncementUseCase {
        return if (type == "club") {
            announcementRepository.findClubAnnouncement()
        } else {
            announcementRepository.findSchoolAnnouncement(uuid)
        }
    }
}