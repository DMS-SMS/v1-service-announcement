package dsm.service.announcement.data.grpc.club

import dsm.service.announcement.proto.GetClubInformWithUUIDResponse
import dsm.service.announcement.proto.GetClubUUIDWithLeaderUUIDResponse
import org.springframework.stereotype.Component

@Component
class ClubService {
    val serviceName: String = "DMS.SMS.v1.service.club"

    suspend fun getClubUuidWithLeaderUuid(uuid: String): GetClubUUIDWithLeaderUUIDResponse? {
        TODO()
    }

    suspend fun getClubWithClubUuid(accountUuid: String, clubUuid: String): GetClubInformWithUUIDResponse? {
        TODO()
    }
}