package dsm.service.announcement.domain.usecases

interface DeleteAnnouncementUseCase {
    fun deleteAnnouncement(announcementUuid: String): String

    fun deleteContent(contentUuid: String)
}