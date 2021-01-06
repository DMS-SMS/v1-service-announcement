package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component

@Component
class GetAnnouncementDetailUseCase(
        private val announcementRepository: AnnouncementRepository
): UseCase<GetAnnouncementDetailUseCase.InputValues, GetAnnouncementDetailUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(getAnnouncement(input))

    private fun getAnnouncement(input: InputValues): Announcement {
        return announcementRepository.findById(input.announcementUuid)
                ?.apply { read(input.accountUuid) }
                ?: throw NotFoundException(message = "Not found Announcement")
    }

    class InputValues(
            val announcementUuid: String,
            val accountUuid: String
    ): UseCase.InputValues

    class OutputValues(
            val announcement: Announcement
    ): UseCase.OutputValues
}