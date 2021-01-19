package dsm.service.announcement.core.domain.repository

import dsm.service.announcement.core.domain.entity.Club
import org.springframework.stereotype.Repository

@Repository
interface ClubRepository {
    fun findClubUuidByLeaderUuid(leaderUuid: String): String?

    fun findByUuid(clubUuid: String, accountUuid: String): Club?
}