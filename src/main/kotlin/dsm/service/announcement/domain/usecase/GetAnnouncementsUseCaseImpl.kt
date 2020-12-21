package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.StudentRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsUseCaseImpl(
        val announcementRepository: AnnouncementRepository,
        val studentRepository: StudentRepository
): GetAnnouncementsUseCase {
    override fun execute(accountUuid: String, type: String, start: Int, count: Int): MutableIterable<Announcement> {
        return if (type == "club") {
            announcementRepository.findByType(type, PageRequest.of(start,count))
        } else {
            studentRepository.findByUuid(accountUuid)?.let {
                println(it.grade)
                println(it.grade.javaClass.name)
                println(it.grade.toString())
                println(it.group.toString())
                announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContains(
                        "school", "2", "1", PageRequest.of(start, count))
            }
            announcementRepository.findByType(type, PageRequest.of(start,count))
        }
    }
}