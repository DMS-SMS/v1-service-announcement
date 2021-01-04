package dsm.service.announcement.v1.domain.usecase

import dsm.service.announcement.v1.domain.exception.NotFoundException
import dsm.service.announcement.v1.domain.exception.UnAuthorizedException
import dsm.service.announcement.v1.domain.repository.AnnouncementRepository
import dsm.service.announcement.v1.domain.repository.ContentRepository
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