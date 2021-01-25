package dsm.service.announcement.controller.grpc

import dsm.service.announcement.controller.mapper.*
import dsm.service.announcement.core.usecase.UseCaseExecutor
import dsm.service.announcement.core.usecase.announcement.*
import dsm.service.announcement.proto.*
import org.springframework.stereotype.Component

@Component
class AnnouncementService(
    private val useCaseExecutor: UseCaseExecutor,

    private val createAnnouncementUseCase: CreateAnnouncementUseCase,
    private val deleteAnnouncementUseCase: DeleteAnnouncementUseCase,
    private val getAnnouncementDetailUseCase: GetAnnouncementDetailUseCase,
    private val getAnnouncementsUseCase: GetAnnouncementsUseCase,
    private val updateAnnouncementsUseCase: UpdateAnnouncementUseCase,
    private val checkAnnouncementUseCase: CheckAnnouncementUseCase,
    private val getMyAnnouncementsUseCase: GetMyAnnouncementsUseCase,
    private val searchAnnouncementsUseCase: SearchAnnouncementsUseCase,

    private val createAnnouncementInputMapper: CreateAnnouncementInputMapper,
    private val createAnnouncementOutputMapper: CreateAnnouncementOutputMapper,
    private val deleteAnnouncementInputMapper: DeleteAnnouncementInputMapper,
    private val deleteAnnouncementOutputMapper: DeleteAnnouncementOutputMapper,
    private val getAnnouncementDetailInputMapper: GetAnnouncementDetailInputMapper,
    private val getAnnouncementDetailOutputMapper: GetAnnouncementDetailOutputMapper,
    private val getAnnouncementsInputMapper: GetAnnouncementsInputMapper,
    private val getAnnouncementsOutputMapper: GetAnnouncementsOutputMapper,
    private val updateAnnouncementInputMapper: UpdateAnnouncementInputMapper,
    private val updateAnnouncementOutputMapper: UpdateAnnouncementOutputMapper,
    private val checkAnnouncementInputMapper: CheckAnnouncementInputMapper,
    private val checkAnnouncementOutputMapper: CheckAnnouncementOutputMapper,
    private val getMyAnnouncementsInputMapper: GetMyAnnouncementsInputMapper,
    private val getMyAnnouncementsOutputMapper: GetMyAnnouncementsOutputMapper,
    private val searchAnnouncementsInputMapper: SearchAnnouncementInputMapper,
    private val searchAnnouncementsOutputMapper: SearchAnnouncementOutputMapper

) {
    fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse {
        return useCaseExecutor.execute(
            createAnnouncementUseCase,
            createAnnouncementInputMapper.map(request),
            createAnnouncementOutputMapper
        )
    }

    fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse {
        return useCaseExecutor.execute(
            deleteAnnouncementUseCase,
            deleteAnnouncementInputMapper.map(request),
            deleteAnnouncementOutputMapper
        )
    }

    fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        return useCaseExecutor.execute(
            getAnnouncementDetailUseCase,
            getAnnouncementDetailInputMapper.map(request),
            getAnnouncementDetailOutputMapper
        )
    }

    fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse {
        return useCaseExecutor.execute(
            getAnnouncementsUseCase,
            getAnnouncementsInputMapper.map(request),
            getAnnouncementsOutputMapper
        )
    }

    fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse {
        return useCaseExecutor.execute(
            updateAnnouncementsUseCase,
            updateAnnouncementInputMapper.map(request),
            updateAnnouncementOutputMapper
        )
    }

    fun checkAnnouncement(request: CheckAnnouncementRequest): CheckAnnouncementResponse {
        return useCaseExecutor.execute(
            checkAnnouncementUseCase,
            checkAnnouncementInputMapper.map(request),
            checkAnnouncementOutputMapper
        )
    }

    fun getMyAnnouncements(request: GetMyAnnouncementsRequest): GetAnnouncementsResponse {
        return useCaseExecutor.execute(
            getMyAnnouncementsUseCase,
            getMyAnnouncementsInputMapper.map(request),
            getMyAnnouncementsOutputMapper
        )
    }

    fun searchAnnouncements(request: SearchAnnouncementsRequest): GetAnnouncementsResponse {
        return useCaseExecutor.execute(
            searchAnnouncementsUseCase,
            searchAnnouncementsInputMapper.map(request),
            searchAnnouncementsOutputMapper
        )
    }
}