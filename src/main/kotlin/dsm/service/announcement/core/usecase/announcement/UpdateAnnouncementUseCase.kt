package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component

@Component
class UpdateAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val getAccountUseCase: GetAccountUseCase
): UseCase<UpdateAnnouncementUseCase.InputValues, UpdateAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(announcementRepository.persist(changeAnnouncement(input)))

    private fun changeAnnouncement(input: InputValues): Announcement {
        return getAnnouncement(input)
                ?.apply {
                    title = input.title
                    content = input.content
                    targetGrade = input.targetGrade
                    targetClass = input.targetGroup
                }
                ?: throw NotFoundException()
    }

    private fun getAnnouncement(input: InputValues): Announcement? {
        return getAccountUseCase.execute(GetAccountUseCase.InputValues(input.writerUuid)).account
                ?.let { account ->
                    if (account.type == AccountType.ADMIN) announcementRepository.findById(input.announcementId)
                    else announcementRepository.findById(input.announcementId)
                            ?.also { if (it.writerUuid != input.writerUuid) throw UnAuthorizedException() }
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
            val announcement: Announcement
    ): UseCase.OutputValues
}