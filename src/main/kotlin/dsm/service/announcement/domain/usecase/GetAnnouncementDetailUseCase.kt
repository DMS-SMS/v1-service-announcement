package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement

interface GetAnnouncementDetailUseCase {
    fun run(announcementUuid: String): Announcement
}