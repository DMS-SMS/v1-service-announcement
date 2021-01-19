package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.GetAnnouncementsUseCase
import dsm.service.announcement.proto.GetAnnouncementsRequest
import org.springframework.stereotype.Component

@Component
class GetAnnouncementsInputMapper {
    fun map(getAnnouncementsRequest: GetAnnouncementsRequest): GetAnnouncementsUseCase.InputValues {
        return GetAnnouncementsUseCase.InputValues(
                accountUuid = getAnnouncementsRequest.uuid,
                type = getAnnouncementsRequest.type,
                start = getAnnouncementsRequest.start,
                count = getAnnouncementsRequest.count
        )
    }
}