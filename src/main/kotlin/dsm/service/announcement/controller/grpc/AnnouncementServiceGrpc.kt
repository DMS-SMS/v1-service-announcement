package dsm.service.announcement.controller.grpc

import dsm.service.announcement.controller.mapper.CreateAnnouncementInputMapper
import dsm.service.announcement.controller.mapper.CreateAnnouncementOutputMapper
import dsm.service.announcement.core.usecase.UseCaseExecutor
import dsm.service.announcement.core.usecase.announcement.CreateAnnouncementUseCase
import dsm.service.announcement.proto.*
import org.lognet.springboot.grpc.GRpcService
import java.util.function.Function

@GRpcService
class AnnouncementServiceGrpc(
        private val useCaseExecutor: UseCaseExecutor,

        private val createAnnouncementUseCase: CreateAnnouncementUseCase,

        private val createAnnouncementInputMapper: CreateAnnouncementInputMapper,
        private val createAnnouncementOutputMapper: CreateAnnouncementOutputMapper
) {
    suspend fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse {
        return useCaseExecutor.execute(
                createAnnouncementUseCase,
                createAnnouncementInputMapper.map(request),
                createAnnouncementOutputMapper
        )
    }

    suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse {
        TODO()
    }

    suspend fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        TODO()
    }

    suspend fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }

    suspend fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse {
        TODO()
    }

    suspend fun checkAnnouncement(request: CheckAnnouncementRequest): CheckAnnouncementResponse {
        TODO()
    }

    suspend fun getMyAnnouncements(request: GetMyAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }

    suspend fun searchAnnouncements(request: SearchAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }
}