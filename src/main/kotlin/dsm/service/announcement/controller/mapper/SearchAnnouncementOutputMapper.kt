package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.usecase.announcement.SearchAnnouncementsUseCase
import dsm.service.announcement.proto.GetAnnouncementsResponse
import org.springframework.stereotype.Component

@Component
class SearchAnnouncementOutputMapper(
        private val announcementPreviewMapper: AnnouncementPreviewMapper
): Mapper<SearchAnnouncementsUseCase.OutputValues, GetAnnouncementsResponse>() {
    override fun map(input: SearchAnnouncementsUseCase.OutputValues): GetAnnouncementsResponse {
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