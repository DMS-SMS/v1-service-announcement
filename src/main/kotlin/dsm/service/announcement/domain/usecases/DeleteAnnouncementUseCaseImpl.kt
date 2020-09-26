package dsm.service.announcement.domain.usecases

import dsm.service.announcement.domain.repositories.AnnouncementRepository

class DeleteAnnouncementUseCaseImpl(
    val announcementRepository: AnnouncementRepository
): DeleteAnnouncementUseCase {
    override fun deleteAnnouncement(announcementUuid: String): String? {
        return announcementRepository.deleteAnnouncement(announcementUuid)
    }

    override fun deleteContent(contentUuid: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}