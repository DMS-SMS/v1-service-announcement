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
         val announcement = GetAnnouncementDetailResponse.newBuilder()
             .setTitle(input.announcement.title)
             .setDate(Timestamp.valueOf(input.announcement.date).time)
             .setContent(input.announcement.content)
             .setWriterName(input.announcement.writerName)
             .setTargetGrade(input.announcement.targetGrade!!.toInt())
             .setTargetGroup(input.announcement.targetClass!!.toInt())
             .setAnnouncementType(input.announcement.type)
        
        if (input.nextAnnouncement != null)
            announcement
                .setNextAnnouncementId(input.nextAnnouncement.uuid)
                .setNextTitle(input.nextAnnouncement.title)

        if (input.previousAnnouncement != null)
            announcement
                .setPreviousAnnouncementId(input.previousAnnouncement.uuid)
                .setPreviousTitle(input.previousAnnouncement.title)

        return announcement
            .setStatus(200)
            .build()
    }
}