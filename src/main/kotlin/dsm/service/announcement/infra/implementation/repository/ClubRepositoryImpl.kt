package dsm.service.announcement.infra.implementation.repository

import dsm.service.announcement.domain.entity.Club
import dsm.service.announcement.domain.repository.ClubRepository
import dsm.service.announcement.infra.club.ClubHandler
import dsm.service.announcement.infra.club.mapper.ClubMapper
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Repository


@Repository
class ClubRepositoryImpl(
        val clubHandler: ClubHandler,
        val clubMapper: ClubMapper
): ClubRepository {
    override fun findByUuid(clubUuid: String, accountUuid: String): Club? = runBlocking {
        return@runBlocking clubMapper.getClubInformWithUuidMapper(
                clubHandler.getClubWithClubUuid(accountUuid, clubUuid)
        )
    }

    override fun findClubUuidByLeaderUuid(leaderUuid: String): String? = runBlocking {
        return@runBlocking clubHandler.getClubUuidWithLeaderUuid(leaderUuid)?.clubUUID
    }
}