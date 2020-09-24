package dsm.service.announcement.application.mapper

import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.proto.CreateAnnouncementRequest
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
            contentUuid = contentUuid
        )
    }
}