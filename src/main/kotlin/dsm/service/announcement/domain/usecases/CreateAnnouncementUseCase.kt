package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement

interface CreateAnnouncementUseCase {
    fun createAnnouncementUseCase(announcement: Announcement)

    fun createContent(content: String): String

    fun createAnnouncementUuidUseCase(): String

    fun createContentUuidUseCase(): String
}