package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Account

interface TeacherRepository {
    fun findById(uuid: String): Account?;
}