package dsm.service.announcement.domain.usecase

import com.mongodb.BasicDBObject
import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.entity.Content
import dsm.service.announcement.domain.entity.View
import dsm.service.announcement.domain.exception.UnAuthorizedException
import dsm.service.announcement.domain.repository.*
import dsm.service.announcement.domain.service.UuidService
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Component
class CreateAnnouncementUseCaseImpl(
        val announcementRepository: AnnouncementRepository,
        val clubRepository: ClubRepository,
        val teacherRepository: TeacherRepository,
        val contentRepository: ContentRepository,
        val viewRepository: ViewRepository,
        val uuidService: UuidService
): CreateAnnouncementUseCase {
    override fun run(
            writerUuid: String,
            title: String,
            content: String,
            targetGrade: Int,
            targetGroup: Int,
            type: String
    ): String {
        var clubName: String? = null
        teacherRepository.findByUuid(writerUuid) ?: run {
            if (type == "school") throw UnAuthorizedException()
            val clubUuid = clubRepository.findClubUuidByLeaderUuid(writerUuid)
            clubUuid?.let {
                clubName = clubRepository.findByUuid(clubUuid, writerUuid)?.name
            }?: throw UnAuthorizedException()
        }

        val announcementUuid = uuidService.createAnnouncementUuid()

        announcementRepository.save(
            Announcement(
                    uuid=announcementUuid,
                    writerUuid=writerUuid,
                    date=LocalDateTime.now(),
                    title=title,
                    targetGrade=targetGrade,
                    targetGroup=targetGroup,
                    type=type,
                    club=clubName
            )
        )

        contentRepository.save(
                Content(announcementUuid, BasicDBObject.parse(content))
        )

        viewRepository.save(
                View(announcementUuid, mutableListOf())
        )
        return announcementUuid;
    }
}