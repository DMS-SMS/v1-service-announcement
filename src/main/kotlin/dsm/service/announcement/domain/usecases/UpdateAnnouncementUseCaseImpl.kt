package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.repositories.AnnouncementRepository

class UpdateAnnouncementUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): UpdateAnnouncementUseCase {
    override fun updateAnnouncement(announcementUuid: String, title: String, targetGrade: Int, targetClass: Int): String? {
        return announcementRepository.updateAnnouncement(announcementUuid, title, targetGrade, targetClass)
    }

    override fun updateContent(contentUuid: String, content: String) {
        return announcementRepository.updateContent(contentUuid, content)
    }
}