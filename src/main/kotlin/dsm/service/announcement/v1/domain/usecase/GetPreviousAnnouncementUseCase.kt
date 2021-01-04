package dsm.service.announcement.v1.domain.usecase

import dsm.service.announcement.v1.domain.entity.Announcement

interface GetPreviousAnnouncementUseCase {
    fun execute(currentAnnouncement: Announcement): Announcement?
}