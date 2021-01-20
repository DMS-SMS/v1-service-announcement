package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.usecase.announcement.GetAnnouncementsUseCase
import dsm.service.announcement.proto.GetAnnouncementsResponse
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsOutputMapper(
        private val announcementPreviewMapper: AnnouncementPreviewMapper
): Mapper<GetAnnouncementsUseCase.OutputValues, GetAnnouncementsResponse>() {
    override fun map(input: GetAnnouncementsUseCase.OutputValues): GetAnnouncementsResponse {
        val responseBuilder = GetAnnouncementsResponse.newBuilder()

        for (announcement: Announcement in input.announcements) {
            responseBuilder.addAnnouncement(announcementPreviewMapper.mapItems(input.accountUuid, announcement))
        }

        return responseBuilder
                .setSize(input.count)
                .setStatus(200)
                .build()
    }
}