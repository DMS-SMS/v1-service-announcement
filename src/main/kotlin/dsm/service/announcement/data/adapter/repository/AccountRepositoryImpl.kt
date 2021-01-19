package dsm.service.announcement.data.adapter.repository

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.core.domain.repository.AccountRepository
import dsm.service.announcement.data.adapter.repository.mapper.AccountRepositoryMapper
import dsm.service.announcement.data.grpc.auth.AuthService
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    val accountRepositoryMapper: AccountRepositoryMapper,
    val authService: AuthService
): AccountRepository {
    override fun findAdminByUuid(adminUuid: String, accountUuid: String): Account? {
        return Account(grade = 0, group = 0, name = "어드민", phoneNumber = "0", type = AccountType.ADMIN)
    }

    override fun findParentsByUuid(parentsUuid: String, accountUuid: String): Account? = runBlocking {
        return@runBlocking accountRepositoryMapper.map(authService.getParentInform(parentsUuid, accountUuid))
    }

    override fun findStudentByUuid(studentUuid: String, accountUuid: String): Account? = runBlocking {
        return@runBlocking accountRepositoryMapper.map(authService.getStudentInform(studentUuid, accountUuid))
    }

    override fun findTeacherByUuid(teacherUuid: String, accountUuid: String): Account? = runBlocking {
        return@runBlocking accountRepositoryMapper.map(authService.getTeacherInform(teacherUuid, accountUuid))
    }
}