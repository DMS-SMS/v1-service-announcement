package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.exception.NotFoundException
import dsm.service.announcement.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Component

@Component
class GetAnnouncementDetailUseCaseImpl(
        var announcementRepository: AnnouncementRepository
): GetAnnouncementDetailUseCase {
    override fun run(announcementUuid: String): Announcement {
        return announcementRepository.findByUuid(announcementUuid) ?: throw NotFoundException()
    }
}