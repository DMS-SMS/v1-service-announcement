package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.usecase.announcement.GetAnnouncementDetailUseCase
import dsm.service.announcement.core.usecase.announcement.GetNextAnnouncementUseCase
import dsm.service.announcement.core.usecase.announcement.GetPreviousAnnouncementUseCase
import dsm.service.announcement.proto.GetAnnouncementDetailResponse
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class GetAnnouncementDetailOutputMapper(
        private val getNextAnnouncementUseCase: GetNextAnnouncementUseCase,
        private val getPreviousAnnouncementUseCase: GetPreviousAnnouncementUseCase
): Mapper<GetAnnouncementDetailUseCase.OutputValues, GetAnnouncementDetailResponse>() {
    override fun map(input: GetAnnouncementDetailUseCase.OutputValues): GetAnnouncementDetailResponse {
        return GetAnnouncementDetailResponse.newBuilder()
                .setTitle(input.announcement.title)
                .setContent(input.announcement.content)
                .setDate(Timestamp.valueOf(input.announcement.date).time)
                .setTargetGrade(input.announcement.targetGrade!!.toInt())
                .setTargetGroup(input.announcement.targetClass!!.toInt())
                .setNextAnnouncementId(generateNextAnnouncementUuid(input))
                .setPreviousAnnouncementId(generatePreviousAnnouncementUuid(input))
                .build()
    }

    private fun generateNextAnnouncementUuid(input: GetAnnouncementDetailUseCase.OutputValues) =
            getNextAnnouncementUseCase.execute(GetNextAnnouncementUseCase
                    .InputValues(input.announcement.writerUuid, input.announcement)).announcement?.uuid

    private fun generatePreviousAnnouncementUuid(input: GetAnnouncementDetailUseCase.OutputValues) =
            getPreviousAnnouncementUseCase.execute(GetPreviousAnnouncementUseCase
                    .InputValues(input.announcement.writerUuid, input.announcement)).announcement?.uuid
}