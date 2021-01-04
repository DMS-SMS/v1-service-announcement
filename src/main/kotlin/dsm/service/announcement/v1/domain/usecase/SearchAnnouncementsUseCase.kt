package dsm.service.announcement.v1.domain.usecase

import dsm.service.announcement.v1.domain.entity.Announcement

interface SearchAnnouncementsUseCase {
    fun execute(accountUuid: String, type: String, query: String, start: Int, count: Int ):
            Pair<MutableIterable<Announcement>, Long>
}