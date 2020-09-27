package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.repositories.AnnouncementRepository

class ValidateUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): ValidateUseCase {
    override fun validateAnnouncementAuthority(announcementUuid: String, userUuid: String) {
        val announcement = announcementRepository.findByUuid(announcementUuid)
    }
}