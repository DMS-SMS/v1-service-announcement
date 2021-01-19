package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.GetAnnouncementsUseCase
import dsm.service.announcement.proto.GetAnnouncementsResponse
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsOutputMapper(
        private val announcementPreviewMapper: AnnouncementPreviewMapper
): Mapper<GetAnnouncementsUseCase.OutputValues, GetAnnouncementsResponse>() {
    override fun map(input: GetAnnouncementsUseCase.OutputValues): GetAnnouncementsResponse {
        return GetAnnouncementsResponse
                .newBuilder()
                .setSize(input.count)
                .addAnnouncement(announcementPreviewMapper.mapItems(input.accountUuid, input.announcements))
                .setStatus(200)
                .build()
    }
}