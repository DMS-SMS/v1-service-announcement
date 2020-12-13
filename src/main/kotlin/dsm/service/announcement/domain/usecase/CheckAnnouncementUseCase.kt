package dsm.service.announcement.domain.usecase

interface CheckAnnouncementUseCase {
    fun execute(uuid: String, type: String): Int
}