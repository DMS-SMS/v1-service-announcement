package dsm.service.announcement.core.usecase.announcement

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Announcement
import dsm.service.announcement.core.domain.exception.NotFoundException
import dsm.service.announcement.core.domain.exception.UnAuthorizedException
import dsm.service.announcement.core.domain.repository.AccountRepository
import dsm.service.announcement.core.domain.repository.AnnouncementRepository
import dsm.service.announcement.core.usecase.UseCase
import org.springframework.stereotype.Component


@Component
class GetAccountUseCase(
        private val accountRepository: AccountRepository
): UseCase<GetAccountUseCase.InputValues, GetAccountUseCase.OutputValues>() {
    override fun execute(input: InputValues): OutputValues =
            OutputValues(generateAccount(input))

    private fun generateAccount(input: InputValues): Account? {
        return when(input.accountUuid.substring(0, input.accountUuid.length - 13)) {
            "admin" -> generateAdminAccount(input)
            "student" -> generateStudentAccount(input)
            "teacher" -> generateTeacherAccount(input)
            "parent" -> generateParentsAccount(input)
            else -> null
        }
    }

    private fun generateAdminAccount(input: InputValues): Account? =
            accountRepository.findAdminByUuid(input.accountUuid, input.accountUuid)

    private fun generateStudentAccount(input: InputValues): Account? =
            accountRepository.findStudentByUuid(input.accountUuid, input.accountUuid)

    private fun generateTeacherAccount(input: InputValues): Account? =
            accountRepository.findTeacherByUuid(input.accountUuid, input.accountUuid)

    private fun generateParentsAccount(input: InputValues): Account? =
            accountRepository.findParentsByUuid(input.accountUuid, input.accountUuid)

    class InputValues(
            val accountUuid: String
    ): UseCase.InputValues

    class OutputValues(
            val account: Account?
    ): UseCase.OutputValues
}