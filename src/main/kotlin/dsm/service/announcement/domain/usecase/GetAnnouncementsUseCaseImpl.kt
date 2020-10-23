package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.StudentRepository
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsUseCaseImpl(
        val announcementRepository: AnnouncementRepository,
        val studentRepository: StudentRepository
): GetAnnouncementsUseCase {
    override fun run(accountUuid: String, type: String): MutableIterable<Announcement> {
        return if (type == "club") {
            announcementRepository.findAllByType(type)
        } else {
            val student = studentRepository.findByUuid(accountUuid)
            student?.let {
                return if (student.group == 0 || student.grade == 0) announcementRepository.findAll()
                else announcementRepository.findAllByTypeAndTargetGradeAndTargetGroup("school", student.grade, student.group)
            }
            announcementRepository.findAll()
        }
    }
}