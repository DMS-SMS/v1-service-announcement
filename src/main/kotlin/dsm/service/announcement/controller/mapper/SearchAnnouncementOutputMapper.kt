package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.SearchAnnouncementsUseCase
import dsm.service.announcement.proto.GetAnnouncementsResponse
import org.springframework.stereotype.Component

@Component
class SearchAnnouncementOutputMapper(
        private val announcementPreviewMapper: AnnouncementPreviewMapper
): Mapper<SearchAnnouncementsUseCase.OutputValues, GetAnnouncementsResponse>() {
    override fun map(input: SearchAnnouncementsUseCase.OutputValues): GetAnnouncementsResponse {
        return GetAnnouncementsResponse
                .newBuilder()
                .setSize(input.count)
                .addAnnouncement(announcementPreviewMapper.mapItems(input.accountUuid, input.announcements))
                .setStatus(201)
                .build()
    }
}