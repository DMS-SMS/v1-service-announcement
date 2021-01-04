package dsm.service.announcement.v1.domain.usecase

import dsm.service.announcement.v1.domain.entity.Announcement

interface GetAnnouncementsUseCase {
    fun execute(accountUuid: String, type: String, start: Int, count: Int): Pair<MutableIterable<Announcement>, Long>
}