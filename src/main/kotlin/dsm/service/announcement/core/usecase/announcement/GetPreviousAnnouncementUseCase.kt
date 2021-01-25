package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.exception.ServerException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component

@Component
class GetPreviousAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val getAccountUseCase: GetAccountUseCase
): UseCase<GetPreviousAnnouncementUseCase.InputValues, GetPreviousAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(getPreviousAnnouncement(input))

    private fun getPreviousAnnouncement(input: InputValues): Announcement? {
        val minNumber = announcementRepository.findTopByOrderByNumberAsc()?.number
                ?: return null
        var number: Long = input.announcement.number
                ?: throw ServerException(message = "Announcement number isn't exists.")

        while (number > minNumber) {
            number -= 1
            val announcement = announcementRepository.findByNumberAndType(number, input.announcement.type)
            announcement?.let {
                when {
                    input.announcement.type == "club" -> return announcement
                    input.announcement.type == "school" && checkAccount(announcement, input) -> return announcement
                    else -> null
                }
            }
        }
        return null
    }

    private fun checkAccount(announcement: Announcement, input: InputValues): Boolean {
        val account = getAccount(input)

        if (account.type != AccountType.STUDENT) return true

        return if (announcement.targetClass != null && announcement.targetGrade != null)
            announcement.targetGrade!!.contains(account.grade.toString()) &&
                    announcement.targetClass!!.contains(account.group.toString())
        else false
    }

    private fun getAccount(input: InputValues): Account =
            getAccountUseCase.execute(GetAccountUseCase.InputValues(input.accountUuid)).account
                    ?: throw UnAuthorizedException()

    class InputValues(
            val accountUuid: String,
            val announcement: Announcement
    ): UseCase.InputValues

    class OutputValues(
            val announcement: Announcement?
    ): UseCase.OutputValues

}