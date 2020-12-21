package dsm.service.announcement.domain.usecase

import com.mongodb.BasicDBObject
import dsm.service.announcement.domain.exception.NotFoundException
import dsm.service.announcement.domain.exception.UnAuthorizedException
import dsm.service.announcement.domain.repository.AnnouncementRepository
import dsm.service.announcement.domain.repository.ContentRepository
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component


@Component
class UpdateAnnouncementUseCaseImpl(
        var announcementRepository: AnnouncementRepository,
        var contentRepository: ContentRepository
): UpdateAnnouncementUseCase {
    override fun execute(writerUuid: String, announcementUuid: String, title: String, content: String, targetGrade: Int, targetGroup: Int) {
        announcementRepository.findByUuid(announcementUuid)?.let {
                if (it.writerUuid != writerUuid) throw UnAuthorizedException()
                it.title = title
                it.targetGrade = targetGrade.toString()
                it.targetGroup = targetGroup.toString()
                announcementRepository.save(it)
                contentRepository.findByUuid(announcementUuid)?.let {
                    it.content = BasicDBObject.parse(content)
                    contentRepository.save(it)
                }
        }?: throw NotFoundException()
    }
}