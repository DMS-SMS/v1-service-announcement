package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.CreateAnnouncementUseCase
import dsm.service.announcement.proto.CreateAnnouncementRequest
import org.springframework.stereotype.Component

@Component
class CreateAnnouncementInputMapper {
    fun map(createAnnouncementRequest: CreateAnnouncementRequest): CreateAnnouncementUseCase.InputValues {
        return CreateAnnouncementUseCase.InputValues(
                writerUuid = createAnnouncementRequest.uuid,
                title = createAnnouncementRequest.title,
                content = createAnnouncementRequest.content,
                targetGrade = createAnnouncementRequest.targetGrade.toString(),
                targetGroup = createAnnouncementRequest.targetGroup.toString(),
                type = createAnnouncementRequest.type
        )
    }
}