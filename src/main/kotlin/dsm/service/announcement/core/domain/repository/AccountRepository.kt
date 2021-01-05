package dsm.service.announcement.core.domain.repository

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.Club
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository {
    fun findAdminByUuid(adminUuid: String, accountUuid: String): Account?

    fun findStudentByUuid(studentUuid: String, accountUuid: String): Account?

    fun findTeacherByUuid(teacherUuid: String, accountUuid: String): Account?

    fun findParentsByUuid(parentsUuid: String, accountUuid: String): Account?
}