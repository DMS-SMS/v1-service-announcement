package dsm.service.announcement.v1.domain.usecase

interface GetContentUseCase {
    fun execute(announcementUuid: String): String
}