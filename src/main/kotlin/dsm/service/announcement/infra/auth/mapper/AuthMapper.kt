package dsm.service.announcement.infra.auth.mapper

import dsm.service.announcement.domain.entity.Account
import dsm.service.announcement.proto.GetStudentInformWithUUIDResponse
import dsm.service.announcement.proto.GetTeacherInformWithUUIDResponse
import org.springframework.stereotype.Component

@Component
class AuthMapper {
    fun getStudentInformWithUuidMapper(response: GetStudentInformWithUUIDResponse?): Account? {
        response?.let { return Account(response.grade, response.group, response.name, response.phoneNumber) }?:
                return null
    }

    fun getTeacherInformWithUuidMapper(response: GetTeacherInformWithUUIDResponse?): Account? {
        response?.let { return Account(response.grade, response.group, response.name, response.phoneNumber) }?:
        return null
    }
}