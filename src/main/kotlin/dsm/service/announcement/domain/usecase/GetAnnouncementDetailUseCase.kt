package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement

interface GetAnnouncementDetailUseCase {
    fun execute(announcementUuid: String, accountUuid: String): Announcement
}