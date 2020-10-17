package dsm.service.announcement.infra.auth.mapper

import dsm.service.announcement.domain.entity.Account
import dsm.service.announcement.proto.GetStudentInformWithUUIDResponse
import org.springframework.stereotype.Component

@Component
class AuthMapper {
    fun getStudentInformWithUuidMapper(response: GetStudentInformWithUUIDResponse): Account {
        return Account(response.grade, response.group, response.name, response.phoneNumber)
    }
}