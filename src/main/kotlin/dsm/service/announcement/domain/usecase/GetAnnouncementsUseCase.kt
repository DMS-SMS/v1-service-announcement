package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement

interface GetAnnouncementsUseCase {
    fun execute(accountUuid: String, type: String, start: Int, count: Int): Pair<MutableIterable<Announcement>, Long>
}