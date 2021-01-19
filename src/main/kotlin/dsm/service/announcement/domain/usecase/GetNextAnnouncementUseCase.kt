package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement

interface GetNextAnnouncementUseCase {
    fun execute(currentAnnouncement: Announcement, accountUuid: String): Announcement?
}