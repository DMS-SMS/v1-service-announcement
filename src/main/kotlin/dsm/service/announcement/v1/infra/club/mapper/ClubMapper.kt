package dsm.service.announcement.v1.infra.club.mapper

import dsm.service.announcement.v1.domain.entity.Club
import dsm.service.announcement.proto.GetClubInformWithUUIDResponse
import org.springframework.stereotype.Component


@Component
class ClubMapper {
    fun getClubInformWithUuidMapper(response: GetClubInformWithUUIDResponse?): Club? {
        response?.let { return Club(response.name, response.clubConcept, response.introduction) }?:
        return null
    }
}