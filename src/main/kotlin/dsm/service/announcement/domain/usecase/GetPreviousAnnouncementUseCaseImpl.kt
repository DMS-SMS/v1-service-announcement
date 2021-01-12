package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.StudentRepository
import org.springframework.stereotype.Component

@Component
class GetPreviousAnnouncementUseCaseImpl(
        val announcementRepository: AnnouncementRepository,
        val studentRepository: StudentRepository
): GetPreviousAnnouncementUseCase {
    override fun execute(currentAnnouncement: Announcement, accountUuid: String): Announcement? {
        val student = studentRepository.findByUuid(accountUuid)
        val minNumber = announcementRepository.findTopByOrderByNumberAsc()?.number?: return null
        var number = currentAnnouncement.number?: throw Exception()

        while (number > minNumber) {
            number -= 1
            announcementRepository.findByNumber(number)?.let {
                if (currentAnnouncement.type == it.type) {
                    if (currentAnnouncement.type == "club") return it
                    else if (student == null) return it
                    else {
                        if (it.targetGrade.contains(student.grade.toString()) &&
                            it.targetGroup.contains(student.group.toString()))
                            return it
                    }
                }
            }
        }
        return null
    }
}