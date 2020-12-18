package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement

interface GetTeacherAnnouncementsUseCase {
    fun execute(uuid: String, start: Int, count: Int): MutableIterable<Announcement>
}