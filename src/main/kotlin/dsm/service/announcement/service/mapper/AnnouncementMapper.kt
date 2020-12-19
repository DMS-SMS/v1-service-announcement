package dsm.service.announcement.service.mapper

import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.entity.Content
import dsm.service.announcement.domain.repository.ViewRepository
import dsm.service.announcement.domain.usecase.GetAccountUseCase
import dsm.service.announcement.proto.AnnouncementPreview
import dsm.service.announcement.proto.GetAnnouncementDetailResponse
import dsm.service.announcement.proto.GetAnnouncementsResponse
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
public class AnnouncementMapper(
        val viewRepository: ViewRepository,
        val getAccountUseCase: GetAccountUseCase
) {
    fun getAnnouncementsMapper(announcements: MutableIterable<Announcement>): GetAnnouncementsResponse.Builder {
        val response = GetAnnouncementsResponse.newBuilder();

        for(announcement: Announcement in announcements) {
            val previewBuilder = AnnouncementPreview.newBuilder()
            previewBuilder
                    .setAnnouncementId(announcement.uuid)
                    .setTitle(announcement.title)
                    .setDate(Timestamp.valueOf(announcement.date).time)
                    .setIsChecked(0)
            viewRepository.findByUuid(announcement.uuid)
                    ?.let {
                        /*
                        print(it.read_accounts)
                        it.read_accounts.size.toLong()
                                .let { size ->
                                    previewBuilder.setViews(size)

                                }
                        it.read_accounts.find { it.contains(announcement.writerUuid) }
                                .let {
                                    previewBuilder.setIsChecked(1)
                                }
                        */

                        it.read_accounts.count()
                                .let { size ->
                                    previewBuilder.setViews(size.toLong())
                                }

                        for( read in it.read_accounts) {
                            if ( read.contains(announcement.writerUuid)) {
                                println(it)
                                previewBuilder.setIsChecked(1)
                            }
                        }
                        /*
                        it.read_accounts
                                .takeIf {
                                    it.contains(announcement.writerUuid)
                                }
                                ?.also {
                                    println(it)
                                    previewBuilder.setIsChecked(1)
                                }*/
                    }

            announcement.number?.let {
                previewBuilder.setNumber(it)
            }

            announcement.writerUuid.let {
                val userName: String = getAccountUseCase.execute(announcement.writerUuid)?.name ?: ""
                previewBuilder.setWriterName(userName)
            }

            announcement.club?.let {
                previewBuilder.setWriterName(it)
            }

            response.addAnnouncement(previewBuilder.build())
        }

        return response
    }

    fun getAnnouncementDetailMapper(
            announcement: Announcement, content: String, nextAnnouncement: Announcement?, previousAnnouncement: Announcement?
    ): GetAnnouncementDetailResponse.Builder {
        val response = GetAnnouncementDetailResponse.newBuilder();
        response.title = announcement.title
        response.date = Timestamp.valueOf(announcement.date).time
        response.content = content

        getAccountUseCase.execute(announcement.writerUuid).let {
            if (it != null) {
                response.writerName = it.name
            }
        }

        nextAnnouncement?.let {
            response.nextAnnouncementId = nextAnnouncement.uuid
            response.nextTitle = nextAnnouncement.title
        }

        previousAnnouncement?.let {
            response.previousAnnouncementId = previousAnnouncement.uuid
            response.previousTitle = previousAnnouncement.title
        }
        return response
    }
}