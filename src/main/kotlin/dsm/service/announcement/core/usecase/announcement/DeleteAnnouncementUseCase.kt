package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component

@Component
class DeleteAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository
): UseCase<DeleteAnnouncementUseCase.InputValues, DeleteAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(deleteAnnouncement(getAnnouncement(input)))

    private fun getAnnouncement(input: InputValues): Announcement {
        return announcementRepository.findById(input.announcementUuid)
                ?.takeIf { it.writerUuid == input.writerUuid }
                ?.also { throw UnAuthorizedException() }
                ?: throw NotFoundException()
    }

    private fun deleteAnnouncement(announcement: Announcement): Announcement {
        announcementRepository.delete(announcement)
        return announcement
    }

    class InputValues(
            val writerUuid: String,
            val announcementUuid: String
    ): UseCase.InputValues

    class OutputValues(
            val announcement: Announcement
    ): UseCase.OutputValues
}