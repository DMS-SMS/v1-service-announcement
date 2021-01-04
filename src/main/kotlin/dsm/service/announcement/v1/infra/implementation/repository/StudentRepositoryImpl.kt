package dsm.service.announcement.v1.infra.implementation.repository

import dsm.service.announcement.v1.domain.entity.Account
import dsm.service.announcement.v1.domain.repository.StudentRepository
import dsm.service.announcement.v1.infra.auth.AuthHandler
import dsm.service.announcement.v1.infra.auth.mapper.AuthMapper
import org.springframework.stereotype.Repository
import kotlinx.coroutines.runBlocking


@Repository
class StudentRepositoryImpl(
        val authHandler: AuthHandler,
        val authMapper: AuthMapper
): StudentRepository {
    override fun findByUuid(uuid: String): Account? = runBlocking {
        return@runBlocking authMapper.getStudentInformWithUuidMapper(
                authHandler.getStudentInform(uuid)
        )
    }
}