package dsm.service.announcement.data.adapter.repository.mapper

import dsm.service.announcement.core.domain.entity.Club
import dsm.service.announcement.proto.GetClubInformWithUUIDResponse
import dsm.service.announcement.proto.GetClubUUIDWithLeaderUUIDResponse
import org.springframework.stereotype.Component

@Component
class ClubRepositoryMapper {
    fun map(proto: GetClubUUIDWithLeaderUUIDResponse?): String? {
        if (proto == null) return null
        return proto.clubUUID
    }

    fun map(proto: GetClubInformWithUUIDResponse?): Club? {
        if (proto == null) return null
        return Club(
            name = proto.name,
            clubConcept = proto.clubConcept,
            introduction = proto.introduction
        )
    }
}