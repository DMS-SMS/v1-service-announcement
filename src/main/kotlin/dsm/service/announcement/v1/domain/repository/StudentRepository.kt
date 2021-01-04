package dsm.service.announcement.v1.domain.repository

import dsm.service.announcement.v1.domain.entity.Account

interface StudentRepository {
    fun findByUuid(uuid: String): Account?
}