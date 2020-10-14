package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Content
import dsm.service.announcement.domain.exception.NotFoundException
import dsm.service.announcement.domain.repository.ContentRepository
import org.springframework.stereotype.Component

@Component
class GetContentUseCaseImpl(
        var contentRepository: ContentRepository
): GetContentUseCase {
    override fun run(announcementUuid: String): String {
        val content: Content = contentRepository.findByUuid(announcementUuid)?: throw NotFoundException()
        return content.content.toJson()
    }
}