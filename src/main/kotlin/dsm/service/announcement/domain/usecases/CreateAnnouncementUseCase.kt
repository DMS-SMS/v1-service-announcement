package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.entities.Announcement

interface CreateAnnouncementUseCase {
    fun createAnnouncement(announcement: Announcement)

    fun createContent(content: String): String

    fun createAnnouncementUuid(): String

    fun createContentUuid(): String
}