package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Component

@Component
class GetNextAnnouncementUseCaseImpl(
        val announcementRepository: AnnouncementRepository
): GetNextAnnouncementUseCase {
    override fun run(currentAnnouncement: Announcement): Announcement? {
        val maxNumber = announcementRepository.findTopByOrderByNumberDesc()?.number?: return null
        var number = currentAnnouncement.number?: throw Exception()

        while (number < maxNumber) {
            number += 1
            announcementRepository.findByNumber(number)?.let {
                return it
            }
        }
        return null
    }
}