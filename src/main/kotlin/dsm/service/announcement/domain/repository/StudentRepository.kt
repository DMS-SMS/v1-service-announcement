package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Account

interface StudentRepository {
    fun findByUuid(uuid: String, xRequestId: String): Account?
}