package dsm.service.announcement.domain.usecase

interface GetContentUseCase {
    fun run(announcementUuid: String): String
}