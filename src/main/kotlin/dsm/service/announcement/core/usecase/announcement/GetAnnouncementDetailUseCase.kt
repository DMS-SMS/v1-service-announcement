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
                            nextAnnouncement = getNextAnnouncement(input.accountUuid, announcement),
                            previousAnnouncement = getPreviousAnnouncement(input.accountUuid, announcement))
                }
                ?: throw NotFoundException(message = "Not found Announcement")
    }

    private fun getNextAnnouncement(accountUuid: String, announcement: Announcement): Announcement? {
        return getNextAnnouncementUseCase.execute(GetNextAnnouncementUseCase
                .InputValues(accountUuid, announcement)).announcement
    }

    private fun getPreviousAnnouncement(accountUuid: String, announcement: Announcement): Announcement? {
        return getPreviousAnnouncementUseCase.execute(GetPreviousAnnouncementUseCase
                .InputValues(accountUuid, announcement)).announcement
    }

    class InputValues(
            val announcementUuid: String,
            val accountUuid: String
    ): UseCase.InputValues

    class OutputValues(
            val announcement: Announcement,
            val nextAnnouncement: Announcement?,
            val previousAnnouncement: Announcement?
    ): UseCase.OutputValues
}