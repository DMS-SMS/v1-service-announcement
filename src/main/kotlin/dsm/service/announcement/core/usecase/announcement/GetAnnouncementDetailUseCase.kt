package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component

@Component
class GetAnnouncementDetailUseCase(
        private val announcementRepository: AnnouncementRepository,

        private val getNextAnnouncementUseCase: GetNextAnnouncementUseCase,
        private val getPreviousAnnouncementUseCase: GetPreviousAnnouncementUseCase
): UseCase<GetAnnouncementDetailUseCase.InputValues, GetAnnouncementDetailUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            getAnnouncementDetail(input)

    private fun getAnnouncementDetail(input: InputValues): OutputValues {
        return announcementRepository.findById(input.announcementUuid)
                ?.apply { announcementRepository.persist(read(input.accountUuid)) }
                ?.let { announcement -> OutputValues(
                            announcement = announcement,
                            nextAnnouncementId = generateNextAnnouncementId(input.accountUuid, announcement),
                            previousAnnouncementId = generatePreviousAnnouncementId(input.accountUuid, announcement))
                }
                ?: throw NotFoundException(message = "Not found Announcement")
    }

    private fun generateNextAnnouncementId(accountUuid: String, announcement: Announcement): String? {
        return getNextAnnouncementUseCase.execute(GetNextAnnouncementUseCase
                .InputValues(accountUuid, announcement)).announcement?.uuid
    }

    private fun generatePreviousAnnouncementId(accountUuid: String, announcement: Announcement): String? {
        return getPreviousAnnouncementUseCase.execute(GetPreviousAnnouncementUseCase
                .InputValues(accountUuid, announcement)).announcement?.uuid
    }

    class InputValues(
            val announcementUuid: String,
            val accountUuid: String
    ): UseCase.InputValues

    class OutputValues(
            val announcement: Announcement,
            val nextAnnouncementId: String?,
            val previousAnnouncementId: String?
    ): UseCase.OutputValues
}