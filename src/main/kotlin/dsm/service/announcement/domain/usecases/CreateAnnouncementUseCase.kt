package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement

interface CreateAnnouncementUseCase {
    fun createAnnouncementUseCase(announcement: Announcement)

    fun createAnnouncementUuidUseCase(): String

    fun createContent(content: String): String
}