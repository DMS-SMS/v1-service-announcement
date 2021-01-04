package dsm.service.announcement.v1.domain.repository

import dsm.service.announcement.v1.domain.entity.Account

interface TeacherRepository {
    fun findByUuid(uuid: String): Account?
}