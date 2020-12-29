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
    override fun execute(accountUuid: String, type: String, start: Int, count: Int): Pair<MutableIterable<Announcement>, Long> {
        if (type == "club") {
            return Pair(announcementRepository.findByType(type, PageRequest.of(start,count)),
                    announcementRepository.countByType(type))
        } else {
            studentRepository.findByUuid(accountUuid)?.let {
                if (it.grade == 0) return Pair(
                        announcementRepository.findByType("school", PageRequest.of(start,count)),
                        announcementRepository.countByType(type))
                return Pair(announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContains(
                        "school", it.grade.toString(), it.group.toString(), PageRequest.of(start, count)),
                        announcementRepository.countByTypeAndTargetGradeContainsAndTargetGroupContains(
                                "school", it.grade.toString(), it.group.toString()
                        ))
            }

        }
        return Pair(announcementRepository.findByType("school", PageRequest.of(start,count)),
                announcementRepository.countByType("School"))
    }
}