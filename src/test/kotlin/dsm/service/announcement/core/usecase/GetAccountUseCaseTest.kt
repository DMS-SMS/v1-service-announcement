package dsm.service.announcement.core.usecase

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.repository.AccountRepository
import dsm.service.announcement.core.usecase.announcement.GetAccountUseCase
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito

class GetAccountUseCaseTest: UseCaseTest() {
    @InjectMocks private lateinit var getAccountUseCase: GetAccountUseCase
    @Mock private lateinit var accountRepository: AccountRepository

    @Test fun getStudentAccount() {
        val input = GetAccountUseCase.InputValues("student-111122223333")
        val account = Account(
            grade = 1,
            group = 1,
            phoneNumber = "01011112222",
            name = "손민기",
            type = AccountType.STUDENT
        )

        given(accountRepository.findStudentByUuid(anyString(), anyString())).willReturn(Account(
            grade = 1,
            group = 1,
            phoneNumber = "01011112222",
            name = "손민기",
            type = AccountType.STUDENT
        ))

        val output = getAccountUseCase.execute(input)

        assertEquals(account, output.account)
    }

    @Test fun getTeacherAccount() {
        val input = GetAccountUseCase.InputValues("teacher-111122223333")
        val account = Account(
            grade = 1,
            group = 1,
            phoneNumber = "01011112222",
            name = "손민기",
            type = AccountType.TEACHER
        )

        given(accountRepository.findTeacherByUuid(anyString(), anyString())).willReturn(Account(
            grade = 1,
            group = 1,
            phoneNumber = "01011112222",
            name = "손민기",
            type = AccountType.TEACHER
        ))

        val output = getAccountUseCase.execute(input)

        assertEquals(account, output.account)
    }

    @Test fun getParentAccount() {
        val input = GetAccountUseCase.InputValues("parent-111122223333")
        val account = Account(
            grade = 0,
            group = 0,
            phoneNumber = "01011112222",
            name = "손민기",
            type = AccountType.PARENTS
        )

        given(accountRepository.findParentsByUuid(anyString(), anyString())).willReturn(Account(
            grade = 0,
            group = 0,
            phoneNumber = "01011112222",
            name = "손민기",
            type = AccountType.PARENTS
        ))

        val output = getAccountUseCase.execute(input)

        assertEquals(account, output.account)
    }

    @Test fun getAdminAccount() {
        val input = GetAccountUseCase.InputValues("admin-111122223333")
        val account = Account(
            grade = 0,
            group = 0,
            phoneNumber = "01011112222",
            name = "어드민",
            type = AccountType.ADMIN
        )

        given(accountRepository.findAdminByUuid(anyString(), anyString())).willReturn(Account(
            grade = 0,
            group = 0,
            phoneNumber = "01011112222",
            name = "어드민",
            type = AccountType.ADMIN
        ))

        val output = getAccountUseCase.execute(input)

        assertEquals(account, output.account)
    }

    @Test fun getWrongAccount() {
        val input = GetAccountUseCase.InputValues("worng-111122223333")
        val account = null

        val output = getAccountUseCase.execute(input)

        assertEquals(account, output.account)
    }

    @Test fun getNotExistAccount() {
        val input = GetAccountUseCase.InputValues("student-111122223333")
        val account = null

        given(accountRepository.findStudentByUuid(anyString(), anyString())).willReturn(null)

        val output = getAccountUseCase.execute(input)

        assertEquals(account, output.account)
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}