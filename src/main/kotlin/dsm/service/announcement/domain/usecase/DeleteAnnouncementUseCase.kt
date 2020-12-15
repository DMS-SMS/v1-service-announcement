package dsm.service.announcement.domain.usecase

interface DeleteAnnouncementUseCase {
    fun execute(writerUuid: String, announcementUuid: String)
}