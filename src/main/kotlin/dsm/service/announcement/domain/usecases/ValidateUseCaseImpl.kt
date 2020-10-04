package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.domain.repositories.AuthRepository

class ValidateUseCaseImpl(
    val announcementRepository: AnnouncementRepository,
    val authRepository: AuthRepository
): ValidateUseCase {
    override fun validateAnnouncementAuthority(announcementUuid: String, userUuid: String) {
        announcementRepository.validateAnnouncement(announcementUuid, userUuid)
    }

    override fun validateAuth(uuid: String, option: String) {
        authRepository.validateAuth(uuid, option)
    }
}