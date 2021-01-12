package dsm.service.announcement.data.grpc.auth

import dsm.service.announcement.proto.GetParentInformWithUUIDResponse
import dsm.service.announcement.proto.GetStudentInformWithUUIDResponse
import dsm.service.announcement.proto.GetTeacherInformWithUUIDResponse
import org.springframework.stereotype.Component

@Component
class AuthService {
    val serviceName = "DMS.SMS.v1.service.auth"

    fun getStudentInform(studentUuid: String, accountUuid: String): GetStudentInformWithUUIDResponse? {
        TODO ()
    }

    fun getTeacherInform(teacherUuid: String, accountUuid: String): GetTeacherInformWithUUIDResponse? {
        TODO()
    }

    fun getParentInform(parentUuid: String, accountUuid: String): GetParentInformWithUUIDResponse? {
        TODO()
    }
}