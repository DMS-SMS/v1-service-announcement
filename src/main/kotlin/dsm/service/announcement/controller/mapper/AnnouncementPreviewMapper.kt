package dsm.service.announcement.controller.mapper

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import dsm.service.announcement.proto.AnnouncementPreview
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class AnnouncementPreviewMapper(
        private val getAccountUseCase: GetAccountUseCase
) {
    fun mapItems(announcements: MutableIterable<Announcement>): AnnouncementPreview {
        val previewBuilder = AnnouncementPreview.newBuilder()
        for (announcement in announcements) {
            previewBuilder
                    .setAnnouncementId(announcement.uuid)
                    .setTitle(announcement.title)
                    .setDate(Timestamp.valueOf(announcement.date).time)
                    .setIsChecked(announcement.isCheck)

            getAccountUseCase.execute(GetAccountUseCase.InputValues(announcement.writerUuid)).account
                    ?.let {
                        announcement.readAccounts.count()
                                .let { count -> previewBuilder.setViews(count.toLong()) }
                        previewBuilder.setWriterName(it.name)
                    }
            announcement.number?.let { previewBuilder.setNumber(it) }
        }
        return previewBuilder.build()
    }
}