package dsm.service.announcement.data.adapter.repository

import dsm.service.announcement.core.domain.entity.Club
import dsm.service.announcement.core.domain.repository.ClubRepository
import dsm.service.announcement.data.adapter.repository.mapper.ClubRepositoryMapper
import dsm.service.announcement.data.grpc.club.ClubService
import kotlinx.coroutines.runBlocking

class ClubRepositoryImpl(
    val clubService: ClubService,
    val clubRepositoryMapper: ClubRepositoryMapper
): ClubRepository {
    override fun findByUuid(clubUuid: String, accountUuid: String): Club? = runBlocking {
        return@runBlocking clubRepositoryMapper.map(clubService.getClubWithClubUuid(clubUuid, accountUuid))
    }

    override fun findClubUuidByLeaderUuid(leaderUuid: String): String? = runBlocking {
        return@runBlocking clubRepositoryMapper.map(clubService.getClubUuidWithLeaderUuid(leaderUuid))
    }
}