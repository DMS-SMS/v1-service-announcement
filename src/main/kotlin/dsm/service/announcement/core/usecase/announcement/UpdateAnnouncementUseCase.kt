package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component

@Component
class UpdateAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository
): UseCase<UpdateAnnouncementUseCase.InputValues, UpdateAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(announcementRepository.persist(changeAnnouncement(input)).uuid)

    private fun getAnnouncement(input: InputValues): Announcement {
        return announcementRepository.findById(input.announcementId)
                ?.takeIf { it.writerUuid == input.writerUuid }
                ?.also { throw UnAuthorizedException() }
                ?: throw NotFoundException()
    }

    private fun changeAnnouncement(input: InputValues): Announcement {
        return getAnnouncement(input).apply {
            title = input.title
            content = input.content
            targetGrade = input.targetGrade
            targetClass = input.targetGroup
        }
    }

    class InputValues(
            val writerUuid: String,
            val title: String,
            val content: String,
            val targetGrade: String,
            val targetGroup: String,
            val announcementId: String
    ): UseCase.InputValues

    class OutputValues(
            val announcementUuid: String
    ): UseCase.OutputValues
}