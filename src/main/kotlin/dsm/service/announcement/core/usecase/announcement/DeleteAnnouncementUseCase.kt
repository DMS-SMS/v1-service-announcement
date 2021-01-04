package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase

class DeleteAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository
): UseCase<DeleteAnnouncementUseCase.InputValues, DeleteAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(deleteAnnouncement(getAnnouncement(input)))

    private fun getAnnouncement(input: InputValues): Announcement {
        return announcementRepository.findById(input.announcementId)
                ?.takeIf { it.writerUuid == input.writerUuid }
                ?.also { throw UnAuthorizedException() }
                ?: throw NotFoundException()
    }

    private fun deleteAnnouncement(announcement: Announcement): String {
        announcementRepository.delete(announcement)
        return announcement.uuid
    }

    class InputValues(
            val writerUuid: String,
            val announcementId: String
    ): UseCase.InputValues

    class OutputValues(
            val announcementUuid: String
    ): UseCase.OutputValues
}