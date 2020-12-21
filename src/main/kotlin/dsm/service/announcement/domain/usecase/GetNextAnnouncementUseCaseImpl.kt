package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Component

@Component
class GetNextAnnouncementUseCaseImpl(
        val announcementRepository: AnnouncementRepository
): GetNextAnnouncementUseCase {
    override fun execute(currentAnnouncement: Announcement): Announcement? {
        val maxNumber = announcementRepository.findTopByOrderByNumberDesc()?.number?: return null
        var number = currentAnnouncement.number?: throw Exception()

        while (number < maxNumber) {
            number += 1
            announcementRepository.findByNumber(number)?.let {
                if (currentAnnouncement.type == it.type) {
                    if (currentAnnouncement.type == "club") return it
                    else {
                        if (currentAnnouncement.targetGrade.contains(it.targetGrade.toString()) &&
                                currentAnnouncement.targetGroup.contains(it.targetGroup.toString()))
                            return it
                    }
                }
            }
        }
        return null
    }
}