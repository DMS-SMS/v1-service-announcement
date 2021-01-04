package dsm.service.announcement.v1.domain.usecase

import dsm.service.announcement.v1.domain.entity.Announcement

interface GetAnnouncementDetailUseCase {
    fun execute(announcementUuid: String, accountUuid: String): Announcement
}