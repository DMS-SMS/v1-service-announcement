package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
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
            return announcementRepository.findByNumberAndType(number, input.announcement.type)
                    ?.let {
                        when {
                            input.announcement.type == "club" -> it
                            input.announcement.type == "school" && checkAccount(it, input) -> it
                            else -> null
                        }
                    }
        }
        return null
    }

    private fun checkAccount(announcement: Announcement, input: InputValues): Boolean {
        val account = getAccount(input)

        return if (announcement.targetClass != null && announcement.targetGrade != null)
            announcement.targetClass!!.contains(account.grade.toString()) &&
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