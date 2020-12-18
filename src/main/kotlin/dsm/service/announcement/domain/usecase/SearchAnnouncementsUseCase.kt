package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement

interface SearchAnnouncementsUseCase {
    fun execute(accountUuid: String, type: String, query: String, start: Int, end: Int ): MutableIterable<Announcement>
}