package dsm.service.announcement.core.domain.repository

import dsm.service.announcement.core.domain.entity.Club

interface ClubRepository {
    fun findClubUuidByLeaderUuid(leaderUuid: String): String?

    fun findByUuid(clubUuid: String, accountUuid: String): Club?
}