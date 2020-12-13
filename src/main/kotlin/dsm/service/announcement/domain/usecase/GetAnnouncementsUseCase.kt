package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement

interface GetAnnouncementsUseCase {
    fun run(accountUuid: String, type: String, start: Int, count: Int): MutableIterable<Announcement>
}