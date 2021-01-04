package dsm.service.announcement.v1.domain.usecase

interface DeleteAnnouncementUseCase {
    fun execute(writerUuid: String, announcementUuid: String)
}