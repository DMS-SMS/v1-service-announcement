package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.GetMyAnnouncementsUseCase
import dsm.service.announcement.proto.GetMyAnnouncementsRequest
import org.springframework.stereotype.Component

@Component
class GetMyAnnouncementsInputMapper {
    fun map(getMyAnnouncementsRequest: GetMyAnnouncementsRequest): GetMyAnnouncementsUseCase.InputValues {
        return GetMyAnnouncementsUseCase.InputValues(
                accountUuid = getMyAnnouncementsRequest.uuid,
                start = getMyAnnouncementsRequest.start,
                count = getMyAnnouncementsRequest.count
        )
    }
}