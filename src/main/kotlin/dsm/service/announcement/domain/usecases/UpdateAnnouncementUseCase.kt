package dsm.service.announcement.domain.usecases

interface UpdateAnnouncementUseCase {
    fun updateAnnouncement(announcementUuid: String, title: String, targetGrade: Int, targetClass: Int): String?

    fun updateContent(contentUuid: String, content: String)
}