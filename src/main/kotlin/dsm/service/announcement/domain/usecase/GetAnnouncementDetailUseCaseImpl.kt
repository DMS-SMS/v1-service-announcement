package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.exception.NotFoundException
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.ViewRepository
import org.springframework.stereotype.Component

@Component
class GetAnnouncementDetailUseCaseImpl(
        var announcementRepository: AnnouncementRepository,
        var viewRepository: ViewRepository
): GetAnnouncementDetailUseCase {
    override fun execute(announcementUuid: String, accountUuid: String): Announcement {
        viewRepository.findByUuid(announcementUuid)?.let {
            if (it.read_accounts.find { it == accountUuid } == null) {
                it.read_accounts.add(accountUuid)
                viewRepository.save(it)
            }
        }
        return announcementRepository.findByUuid(announcementUuid) ?: throw NotFoundException()
    }
}