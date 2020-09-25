package dsm.service.announcement.application.mapper

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.proto.AnnouncementPreview
import dsm.service.announcement.proto.CreateAnnouncementRequest
import dsm.service.announcement.proto.GetAnnouncementResponse
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

class AnnouncementMapper {
    fun createAnnouncementRequestMapper(
        createAnnouncementRequest: CreateAnnouncementRequest,
        contentUuid: String
    ): Announcement {
        return Announcement(
            writerUuid = createAnnouncementRequest.uuid,
            type = createAnnouncementRequest.type,
            date = LocalDateTime.now(),
            title = createAnnouncementRequest.title,
            contentUuid = contentUuid,
            targetGrade = createAnnouncementRequest.targetGrade,
            targetClass = createAnnouncementRequest.targetClass
        )
    }

    fun getAnnouncementResponseMapper(
        announcements: List<Announcement?>
    ): GetAnnouncementResponse {
        val getAnnouncementResponse = GetAnnouncementResponse.newBuilder()
            .setStatus(200)

        for(announcement in announcements) {
            if (announcement != null) {
                    getAnnouncementResponse
                        .addAnnouncement(
                            AnnouncementPreview.newBuilder()
                                .setNumber(1)
                                .setDate(
                                    Timestamp.valueOf(announcement.date).time)
                                .setTitle(announcement.title)
                                .setType(announcement.type)
                                .setViews(1).build()
                        )
                }
            }

        return getAnnouncementResponse.build()
    }
    fun getAnnouncementResponseMapper(
        announcement: Announcement?,
        content: Document?
    ): GetAnnouncementDetailResponse {
        var response = GetAnnouncementDetailResponse.newBuilder()

        if (announcement != null && content != null) {
            response
                .setStatus(200)
                .setTitle(announcement.title)
                .setDate(Timestamp.valueOf(announcement.date).time)
                .setContent(content.toJson())
        }

        return response.build()
    }
}