package dsm.service.announcement.domain.repository

import dsm.service.announcement.domain.entity.Club

interface ClubRepository {
    fun findClubUuidByLeaderUuid(leaderUuid: String, xRequestId: String): String

    fun findByUuid(clubUuid: String, accountUuid: String, xRequestId: String): Club
}