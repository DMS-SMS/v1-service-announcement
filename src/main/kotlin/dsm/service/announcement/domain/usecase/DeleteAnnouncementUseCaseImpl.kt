package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.exception.NotFoundException
import dsm.service.announcement.domain.exception.UnAuthorizedException
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.ContentRepository
import org.springframework.stereotype.Component

@Component
class DeleteAnnouncementUseCaseImpl(
        val announcementRepository: AnnouncementRepository,
        val contentRepository: ContentRepository
): DeleteAnnouncementUseCase {
    override fun execute(writerUuid: String, announcementUuid: String) {
        announcementRepository.findByUuid(announcementUuid)?.let {
            if (it.writerUuid != writerUuid) throw UnAuthorizedException()
            announcementRepository.delete(it);
            contentRepository.deleteByUuid(announcementUuid);
        }?: throw NotFoundException()
    }
}