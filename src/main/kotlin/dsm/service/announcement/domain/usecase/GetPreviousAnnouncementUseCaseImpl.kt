package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Component

@Component
class GetPreviousAnnouncementUseCaseImpl(
        val announcementRepository: AnnouncementRepository
): GetPreviousAnnouncementUseCase {
    override fun run(currentAnnouncement: Announcement): Announcement? {
        val minNumber = announcementRepository.findTopByOrderByNumberAsc()?.number?: return null
        var number = currentAnnouncement.number?: throw Exception()

        while (number > minNumber) {
            number -= 1
            announcementRepository.findByNumber(number)?.let {
                return it
            }
        }
        return null
    }
}