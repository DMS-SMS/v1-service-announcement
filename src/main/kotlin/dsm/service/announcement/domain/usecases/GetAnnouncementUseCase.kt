package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement

interface GetAnnouncementUseCase {
    fun getAnnouncementUseCase(type: String): Announcement
}