package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.DeleteAnnouncementUseCase
import dsm.service.announcement.proto.DefaultAnnouncementResponse
import org.springframework.stereotype.Component

@Component
class DeleteAnnouncementOutputMapper: Mapper<DeleteAnnouncementUseCase.OutputValues, DefaultAnnouncementResponse>() {
    override fun map(input: DeleteAnnouncementUseCase.OutputValues): DefaultAnnouncementResponse {
        return DefaultAnnouncementResponse
                .newBuilder()
                .setAnnouncementId(input.announcement.uuid)
                .setStatus(201)
                .build()
    }
}