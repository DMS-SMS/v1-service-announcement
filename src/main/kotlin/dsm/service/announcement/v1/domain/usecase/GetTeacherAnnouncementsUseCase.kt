package dsm.service.announcement.v1.domain.usecase

import dsm.service.announcement.v1.domain.entity.Announcement

interface GetTeacherAnnouncementsUseCase {
    fun execute(uuid: String, start: Int, count: Int): Pair<MutableIterable<Announcement>, Long>
}