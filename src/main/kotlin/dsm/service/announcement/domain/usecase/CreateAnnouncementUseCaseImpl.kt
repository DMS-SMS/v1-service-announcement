package dsm.service.announcement.domain.usecase

import com.mongodb.BasicDBObject
import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.entity.Content
import dsm.service.announcement.domain.exception.NotFoundException
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
        val uuidService: UuidService
): CreateAnnouncementUseCase {
    override fun run(
            writerUuid: String,
            title: String,
            content: String,
            targetGrade: Int,
            targetGroup: Int,
            type: String
    ) {
        var club: String? = null
        teacherRepository.findByUuid(writerUuid)?.let {
            if (it.grade == 0 || it.group == 0) {
                val clubUuid = clubRepository.findClubUuidByLeaderUuid(writerUuid)
                clubUuid.let {
                    if (it.isEmpty()) throw UnAuthorizedException()
                    if (type == "school") throw UnAuthorizedException() // UnAuth
                }

                club = clubRepository.findByUuid(clubUuid, writerUuid).name
            }
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
    }
}