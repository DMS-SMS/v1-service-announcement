package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.ServerException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AccountRepository
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.domain.repository.ClubRepository
import dsm.service.announcement.core.usecase.UseCase
import java.time.LocalDateTime
import java.util.*
import kotlin.streams.asSequence

class GetNextAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository,
        private val accountRepository: AccountRepository
): UseCase<GetNextAnnouncementUseCase.InputValues, GetNextAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(getNextAnnouncement(input))

    private fun getNextAnnouncement(input: InputValues): Announcement? {
        val maxNumber = announcementRepository.findTopByOrderByNumberDesc()?.number?: return null
        var number: Long = input.announcement.number?: throw ServerException(message = "Announcement number isn't exists.")

        while (number < maxNumber) {
            number += 1
            announcementRepository.findByNumberAndType(number, input.announcement.type)?.let {
                if (input.announcement.type == "club") return it
                if (input.announcement.type == "school" && checkAccount(it, input)) return it
            }
        }
        return null
    }

    private fun checkAccount(announcement: Announcement, input: InputValues): Boolean {
        val account = getAccount(input)?: throw UnAuthorizedException();
        if (announcement.targetGrade != null && announcement.targetClass != null) {
            if (announcement.targetGrade.contains(account.grade.toString()) &&
                            announcement.targetClass.contains(account.group.toString())) return true
        }
        return false
    }

    private fun getAccount(input: InputValues): Account? {
        return accountRepository.findByUuid(input.accountUuid, input.accountUuid)
    }

    class InputValues(
            val accountUuid: String,
            val announcement: Announcement
    ): UseCase.InputValues

    class OutputValues(
            val announcement: Announcement?
    ): UseCase.OutputValues

}