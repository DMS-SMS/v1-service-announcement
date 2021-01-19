package dsm.service.announcement.data.adapter.repository.mapper

import dsm.service.announcement.core.domain.entity.Account
import dsm.service.announcement.core.domain.entity.enums.AccountType
import dsm.service.announcement.proto.GetParentInformWithUUIDResponse
import dsm.service.announcement.proto.GetStudentInformWithUUIDResponse
import dsm.service.announcement.proto.GetTeacherInformWithUUIDResponse
import org.springframework.stereotype.Component

@Component
class AccountRepositoryMapper {
    fun map(proto: GetStudentInformWithUUIDResponse?): Account? {
        if (proto == null) return null
        return Account(
            name = proto.name,
            grade = proto.grade,
            group = proto.group,
            phoneNumber = proto.phoneNumber,
            type = AccountType.STUDENT
        )
    }

    fun map(proto: GetTeacherInformWithUUIDResponse?): Account? {
        if (proto == null) return null
        return Account(
            name = proto.name,
            grade = proto.grade,
            group = proto.group,
            phoneNumber = proto.phoneNumber,
            type = AccountType.TEACHER
        )
    }

    fun map(proto: GetParentInformWithUUIDResponse?): Account? {
        if (proto == null) return null
        return Account(
            name = proto.name,
            grade = 0,
            group = 0,
            phoneNumber = proto.phoneNumber,
            type = AccountType.PARENTS
        )
    }
}