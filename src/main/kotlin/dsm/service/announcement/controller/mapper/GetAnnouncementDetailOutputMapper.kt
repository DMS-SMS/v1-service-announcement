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
            .setDate(Timestamp.valueOf(input.announcement.date).time)
            .setContent(input.announcement.content)
            .setWriterName(input.announcement.writerName)
            .setTargetGrade(input.announcement.targetGrade!!.toInt())
            .setTargetGroup(input.announcement.targetClass!!.toInt())
            .setNextAnnouncementId(if (input.nextAnnouncement != null) input.nextAnnouncement.uuid else null)
            .setNextTitle(if (input.nextAnnouncement != null) input.nextAnnouncement.title else null)
            .setPreviousAnnouncementId(if (input.previousAnnouncement != null) input.previousAnnouncement.uuid else null)
            .setPreviousTitle(if (input.previousAnnouncement != null) input.previousAnnouncement.title else null)
            .setStatus(200)
            .build()
    }
}