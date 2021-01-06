package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.CheckAnnouncementUseCase
import dsm.service.announcement.proto.CheckAnnouncementRequest
import org.springframework.stereotype.Component

@Component
class CheckAnnouncementInputMapper {
    fun map(checkAnnouncementRequest: CheckAnnouncementRequest): CheckAnnouncementUseCase.InputValues {
        return CheckAnnouncementUseCase.InputValues(
                accountUuid = checkAnnouncementRequest.uuid
        )
    }
}