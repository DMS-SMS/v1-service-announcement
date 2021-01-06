package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.GetAnnouncementDetailUseCase
import dsm.service.announcement.proto.GetAnnouncementDetailRequest
import org.springframework.stereotype.Component

@Component
class GetAnnouncementDetailInputMapper {
    fun map(getAnnouncementDetailRequest: GetAnnouncementDetailRequest): GetAnnouncementDetailUseCase.InputValues {
        return GetAnnouncementDetailUseCase.InputValues(
                announcementUuid = getAnnouncementDetailRequest.uuid
        )
    }
}