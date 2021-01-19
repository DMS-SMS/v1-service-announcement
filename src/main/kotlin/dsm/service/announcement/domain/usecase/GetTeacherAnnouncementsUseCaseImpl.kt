package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GetTeacherAnnouncementsUseCaseImpl(
        val announcementRepository: AnnouncementRepository
): GetTeacherAnnouncementsUseCase {
    override fun execute(uuid: String, start: Int, count: Int): Pair<MutableIterable<Announcement>, Long> {
        return Pair(announcementRepository.findByWriterUuidAndTypeOrderByDateDesc(uuid, "school", PageRequest.of(start, count)),
                announcementRepository.countByWriterUuidAndType(uuid, "school"))
    }
}