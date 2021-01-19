package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.GetMyAnnouncementsUseCase
import dsm.service.announcement.proto.GetAnnouncementsResponse
import org.springframework.stereotype.Component

@Component
class GetMyAnnouncementsOutputMapper(
        private val announcementPreviewMapper: AnnouncementPreviewMapper
): Mapper<GetMyAnnouncementsUseCase.OutputValues, GetAnnouncementsResponse>() {
    override fun map(input: GetMyAnnouncementsUseCase.OutputValues): GetAnnouncementsResponse {
        return GetAnnouncementsResponse.newBuilder()
                .setSize(input.count)
                .addAnnouncement(announcementPreviewMapper.mapItems(input.accountUuid, input.announcements))
                .setStatus(200)
                .build()
    }
}