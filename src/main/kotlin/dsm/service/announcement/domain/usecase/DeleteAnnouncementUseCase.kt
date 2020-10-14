package dsm.service.announcement.domain.usecase

interface DeleteAnnouncementUseCase {
    fun run(writerUuid: String, announcementUuid: String)
}