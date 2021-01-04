package dsm.service.announcement.v1.domain.repository

import dsm.service.announcement.v1.domain.entity.Club

interface ClubRepository {
    fun findClubUuidByLeaderUuid(leaderUuid: String): String?

    fun findByUuid(clubUuid: String, accountUuid: String): Club?
}