package dsm.service.announcement.domain.usecase

interface GetContentUseCase {
    fun execute(announcementUuid: String): String
}