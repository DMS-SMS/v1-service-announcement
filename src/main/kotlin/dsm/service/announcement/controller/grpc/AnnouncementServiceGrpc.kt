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
): AnnouncementServiceGrpcKt.AnnouncementServiceCoroutineImplBase() {
    override suspend fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse {
        return useCaseExecutor.execute(
                createAnnouncementUseCase,
                createAnnouncementInputMapper.map(request),
                createAnnouncementOutputMapper
        )
    }

    override suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse {
        TODO()
    }

    override suspend fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        TODO()
    }

    override suspend fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }

    override suspend fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse {
        TODO()
    }

    override suspend fun checkAnnouncement(request: CheckAnnouncementRequest): CheckAnnouncementResponse {
        TODO()
    }

    override suspend fun getMyAnnouncements(request: GetMyAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }

    override suspend fun searchAnnouncements(request: SearchAnnouncementsRequest): GetAnnouncementsResponse {
        TODO()
    }
}