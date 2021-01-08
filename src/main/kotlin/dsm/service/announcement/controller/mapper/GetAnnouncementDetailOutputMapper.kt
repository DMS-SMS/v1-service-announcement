package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.GetAnnouncementDetailUseCase
import dsm.service.announcement.core.usecase.announcement.GetNextAnnouncementUseCase
import dsm.service.announcement.core.usecase.announcement.GetPreviousAnnouncementUseCase
import dsm.service.announcement.proto.GetAnnouncementDetailResponse
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class GetAnnouncementDetailOutputMapper: Mapper<GetAnnouncementDetailUseCase.OutputValues, GetAnnouncementDetailResponse>() {
    override fun map(input: GetAnnouncementDetailUseCase.OutputValues): GetAnnouncementDetailResponse {
        return GetAnnouncementDetailResponse.newBuilder()
                .setTitle(input.announcement.title)
                .setContent(input.announcement.content)
                .setDate(Timestamp.valueOf(input.announcement.date).time)
                .setTargetGrade(input.announcement.targetGrade!!.toInt())
                .setTargetGroup(input.announcement.targetClass!!.toInt())
                .setNextAnnouncementId(input.nextAnnouncementId)
                .setPreviousAnnouncementId(input.previousAnnouncementId)
                .build()
    }
}