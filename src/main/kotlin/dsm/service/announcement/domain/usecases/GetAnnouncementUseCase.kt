package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement

interface GetAnnouncementUseCase {
    fun getAnnouncement(uuid: String, type: String): List<Announcement?>
}