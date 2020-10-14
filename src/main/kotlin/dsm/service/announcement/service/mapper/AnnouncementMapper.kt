package dsm.service.announcement.service.mapper

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.entity.Content
import dsm.service.announcement.proto.AnnouncementPreview
import dsm.service.announcement.proto.GetAnnouncementDetailResponse
import dsm.service.announcement.proto.GetAnnouncementsResponse
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
public class AnnouncementMapper {
    fun getAnnouncementsMapper(announcements: MutableIterable<Announcement>): GetAnnouncementsResponse.Builder {
        val response = GetAnnouncementsResponse.newBuilder();
        println("ADSFASDF")

        for(announcement: Announcement in announcements) {
            val previewBuilder = AnnouncementPreview.newBuilder()
            previewBuilder
                    .setAnnouncementId(announcement.uuid)
                    .setTitle(announcement.title)
                    .setDate(Timestamp.valueOf(announcement.date).time)
                    .setViews(0)

            announcement.number?.let {
                previewBuilder.setNumber(it)
            }

            announcement.club?.let {
                previewBuilder.setClub(it)
            }

            response.addAnnouncement(previewBuilder.build())
        }

        return response
    }

    fun getAnnouncementDetailMapper(announcement: Announcement, content: String): GetAnnouncementDetailResponse.Builder {
        val response = GetAnnouncementDetailResponse.newBuilder();
        response.title = announcement.title
        response.date = Timestamp.valueOf(announcement.date).time
        response.content = content
        return response
    }
}