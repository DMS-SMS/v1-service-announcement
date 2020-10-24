package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement

interface GetPreviousAnnouncementUseCase {
    fun run(currentAnnouncement: Announcement): Announcement?
}