package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.DeleteAnnouncementUseCase
import dsm.service.announcement.proto.CreateAnnouncementRequest
import dsm.service.announcement.proto.DeleteAnnouncementRequest
import org.springframework.stereotype.Component

@Component
class DeleteAnnouncementInputMapper {
    fun map(deleteAnnouncementRequest: DeleteAnnouncementRequest): DeleteAnnouncementUseCase.InputValues {
        return DeleteAnnouncementUseCase.InputValues(
                writerUuid = deleteAnnouncementRequest.uuid,
                announcementId = deleteAnnouncementRequest.announcementId
        )
    }
}