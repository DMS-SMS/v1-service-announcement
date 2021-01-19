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
class CheckAnnouncementUseCase(
        private val announcementRepository: AnnouncementRepository,

        private val getAccountUseCase: GetAccountUseCase
): UseCase<CheckAnnouncementUseCase.InputValues, CheckAnnouncementUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues {
        return OutputValues(
                isAnnouncementCheck(input, "club"),
                isAnnouncementCheck(input, "school")
        )
    }

    private fun isAnnouncementCheck(input: InputValues, type: String): Boolean {
        val account = getStudent(input.accountUuid)

        return when (type) {
            "club" ->  checkReadAnnouncement(input.accountUuid, announcementRepository.findByTypeOrderByDateDesc(type))
            "school" -> checkReadAnnouncement(
                    input.accountUuid,
                    announcementRepository.findByTypeAndTargetGradeContainsAndTargetGroupContainsOrderByDateDesc(
                            type, account.grade.toString(), account.group.toString())
            )
            else -> throw ServerException(message = "This type is not support.")
        }
    }

    private fun getStudent(uuid: String): Account {
        val account = getAccountUseCase.execute(GetAccountUseCase.InputValues(uuid)).account?:
        throw UnAuthorizedException(message = "Your not student")
        if (account.type != AccountType.STUDENT) throw UnAuthorizedException(message = "Your not student")
        return account
    }

    private fun checkReadAnnouncement(studentUuid: String, announcements: MutableIterable<Announcement>): Boolean {
        for (announcement: Announcement in announcements) {
            if (!announcement.readAccounts.contains(studentUuid)) return false }
        return true
    }

    class InputValues(
            val accountUuid: String
    ): UseCase.InputValues

    class OutputValues(
            val clubIsCheck: Boolean,
            val schoolIsCheck: Boolean
    ): UseCase.OutputValues
}