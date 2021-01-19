package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.proto.AnnouncementPreview
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class AnnouncementPreviewMapper {
    fun mapItems(accountUuid: String, announcements: MutableIterable<Announcement>): AnnouncementPreview {
        val previewBuilder = AnnouncementPreview.newBuilder()
        for (announcement in announcements) {
            previewBuilder
                    .setAnnouncementId(announcement.uuid)
                    .setTitle(announcement.title)
                    .setWriterName(announcement.writerName)
                    .setDate(Timestamp.valueOf(announcement.date).time)
                    .setIsChecked(if (announcement.isCheck || announcement.readAccounts.contains(accountUuid)) 1 else 0)
                    .setViews(announcement.readAccounts.count().toLong())
            announcement.number?.let { previewBuilder.setNumber(it) }
        }
        return previewBuilder.build()
    }
}