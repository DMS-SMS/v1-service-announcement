package dsm.service.announcement.v1.domain.usecase

interface CheckAnnouncementUseCase {
    fun execute(uuid: String, type: String): Int
}