package dsm.service.announcement.domain.usecase

import com.mongodb.BasicDBObject
import dsm.service.announcement.domain.entity.Announcement
import dsm.service.announcement.domain.entity.Content
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.ContentRepository
import dsm.service.announcement.domain.repository.TeacherRepository
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
        val contentRepository: ContentRepository,
        val uuidService: UuidService
): CreateAnnouncementUseCase {
    override fun run(writerUuid: String, title: String, content: String, targetGrade: Int, targetGroup: Int, type: String){
        // TODO 동아리장 또는 선생 인증 관련 로직 추가
        println("ASDFASDF")

        val announcementUuid = uuidService.createAnnouncementUuid()

        announcementRepository.save(
            Announcement(
                    uuid=announcementUuid,
                    writerUuid=writerUuid,
                    date=LocalDateTime.now(),
                    title=title,
                    targetGrade=targetGrade,
                    targetGroup=targetGroup,
                    type=type
            )
        )

        contentRepository.save(
                Content(announcementUuid, BasicDBObject.parse(content))
        )
    }
}