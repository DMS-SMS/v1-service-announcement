package dsm.service.announcement.infra.implementation.repository

import dsm.service.announcement.domain.entity.Account
import dsm.service.announcement.domain.repository.TeacherRepository
import dsm.service.announcement.infra.auth.AuthHandler
import dsm.service.announcement.infra.auth.mapper.AuthMapper
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Repository


@Repository
class TeacherRepositoryImpl(
        val authMapper: AuthMapper,
        val authHandler: AuthHandler
): TeacherRepository {
    override fun findByUuid(uuid: String): Account? = runBlocking {
        return@runBlocking authMapper.getTeacherInformWithUuidMapper(
                authHandler.getTeacherInform(uuid)
        )
    }
}