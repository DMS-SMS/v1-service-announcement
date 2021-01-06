package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.CreateAnnouncementUseCase
import dsm.service.announcement.proto.DefaultAnnouncementResponse
import org.springframework.stereotype.Component

@Component
class CreateAnnouncementOutputMapper: Mapper<CreateAnnouncementUseCase.OutputValues, DefaultAnnouncementResponse>() {
    override fun map(input: CreateAnnouncementUseCase.OutputValues): DefaultAnnouncementResponse{
        return DefaultAnnouncementResponse
                .newBuilder()
                .setAnnouncementId(input.announcement.uuid)
                .setStatus(201)
                .build()
    }
}