package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.UpdateAnnouncementUseCase
import dsm.service.announcement.proto.DefaultAnnouncementResponse
import org.springframework.stereotype.Component

@Component
class UpdateAnnouncementOutputMapper: Mapper<UpdateAnnouncementUseCase.OutputValues, DefaultAnnouncementResponse>() {
    override fun map(input: UpdateAnnouncementUseCase.OutputValues): DefaultAnnouncementResponse {
        return DefaultAnnouncementResponse
                .newBuilder()
                .setAnnouncementId(input.announcement.uuid)
                .setStatus(201)
                .build()
    }
}