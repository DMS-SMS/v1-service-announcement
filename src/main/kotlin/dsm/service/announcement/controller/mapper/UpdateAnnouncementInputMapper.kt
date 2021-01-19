package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.UpdateAnnouncementUseCase
import dsm.service.announcement.proto.UpdateAnnouncementRequest
import org.springframework.stereotype.Component

@Component
    class UpdateAnnouncementInputMapper {
    fun map(updateAnnouncementRequest: UpdateAnnouncementRequest): UpdateAnnouncementUseCase.InputValues {
        return UpdateAnnouncementUseCase.InputValues(
                writerUuid = updateAnnouncementRequest.uuid,
                announcementId = updateAnnouncementRequest.announcementId,
                title = updateAnnouncementRequest.title,
                content = updateAnnouncementRequest.content,
                targetGroup = updateAnnouncementRequest.targetGroup.toString(),
                targetGrade = updateAnnouncementRequest.targetGrade.toString()
        )
    }
}