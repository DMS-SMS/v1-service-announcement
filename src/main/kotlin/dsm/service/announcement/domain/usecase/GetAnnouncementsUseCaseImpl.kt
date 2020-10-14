package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsUseCaseImpl(
        val announcementRepository: AnnouncementRepository
): GetAnnouncementsUseCase {
    override fun run(accountUuid: String, type: String): MutableIterable<Announcement> {
        // TODO type 별 공지 관련 로직 추가
        // TODO type == "school"시 group, grade별 분리
        return announcementRepository.findAll()
    }
}