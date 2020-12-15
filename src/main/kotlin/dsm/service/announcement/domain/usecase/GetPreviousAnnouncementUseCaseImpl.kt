package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Component

@Component
class GetPreviousAnnouncementUseCaseImpl(
        val announcementRepository: AnnouncementRepository
): GetPreviousAnnouncementUseCase {
    override fun execute(currentAnnouncement: Announcement): Announcement? {
        val minNumber = announcementRepository.findTopByOrderByNumberAsc()?.number?: return null
        var number = currentAnnouncement.number?: throw Exception()

        while (number > minNumber) {
            number -= 1
            announcementRepository.findByNumber(number)?.let {
                if (currentAnnouncement.type == it.type) {
                    if (currentAnnouncement.type == "club") return it
                    else {
                        if (currentAnnouncement.targetGrade == it.targetGrade &&
                                currentAnnouncement.targetGroup == it.targetGroup ) return it
                    }
                }
            }
        }
        return null
    }
}