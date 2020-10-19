package dsm.service.announcement.infra.club.mapper

import dsm.service.announcement.domain.entity.Club
import dsm.service.announcement.proto.GetClubInformWithUUIDResponse
import org.springframework.stereotype.Component


@Component
class ClubMapper {
    fun getClubInformWithUuidMapper(response: GetClubInformWithUUIDResponse): Club {
        return Club(response.name, response.clubConcept, response.introduction)
    }
}