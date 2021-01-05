package dsm.service.announcement.core.domain.repository

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Club
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository {
    fun findByUuid(uuid: String, accountUuid: String): Account?
}