package dsm.service.announcement.presentation.servicers

import dsm.service.announcement.application.proxy.AnnouncementServiceProxy
import dsm.service.announcement.application.mapper.AnnouncementMapper
import dsm.service.announcement.application.services.announcement.AnnouncementService
import dsm.service.announcement.application.services.announcement.AnnouncementServiceImpl
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.domain.repositories.AuthRepository
import dsm.service.announcement.domain.usecases.*
import dsm.service.announcement.infrastructure.repositories.AnnouncementRepositoryImpl
import dsm.service.announcement.infrastructure.repositories.AuthRepositoryImpl
import dsm.service.announcement.proto.*

class AnnouncementServicer(
    private val announcementRepository: AnnouncementRepository = AnnouncementRepositoryImpl(),
    private val authRepository: AuthRepository = AuthRepositoryImpl(),
    private val announcementServiceImpl: AnnouncementService = AnnouncementServiceImpl(
        announcementMapper = AnnouncementMapper(),
        getAnnouncementUseCase = GetAnnouncementUseCaseImpl(
            announcementRepository
        ),
        createAnnouncementUseCase = CreateAnnouncementUseCaseImpl(
            announcementRepository
        ),
        updateAnnouncementUseCase = UpdateAnnouncementUseCaseImpl(
            announcementRepository
        ),
        deleteAnnouncementUseCase = DeleteAnnouncementUseCaseImpl(
            announcementRepository
        ),
        validateUseCase = ValidateUseCaseImpl(
            announcementRepository,
            authRepository
        )
    ),
    private val announcementService: AnnouncementServiceProxy = AnnouncementServiceProxy(
        announcementServiceImpl
    )
): AnnouncementServiceGrpcKt.AnnouncementServiceCoroutineImplBase() {

    override suspend fun getAnnouncement(request: GetAnnouncementRequest): GetAnnouncementResponse {
        return announcementService.getAnnouncement(request)
    }

    override suspend fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        return announcementService.getAnnouncementDetail(request)
    }

    override suspend fun createAnnouncement(request: CreateAnnouncementRequest): CreateAnnouncementResponse {
        return announcementService.createAnnouncement(request)
    }

    override suspend fun updateAnnouncement(request: UpdateAnnouncementRequest): UpdateAnnouncementResponse {
        return announcementService.updateAnnouncement(request)
    }

    override suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DeleteAnnouncementResponse {
        return announcementService.deleteAnnouncement(request)
    }
}