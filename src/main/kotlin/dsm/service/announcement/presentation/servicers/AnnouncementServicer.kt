package dsm.service.announcement.presentation.servicers

import dsm.service.announcement.application.mapper.AnnouncementMapper
import dsm.service.announcement.application.services.announcement.AnnouncementService
import dsm.service.announcement.application.services.announcement.AnnouncementServiceImpl
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.domain.usecases.CreateAnnouncementUseCaseImpl
import dsm.service.announcement.domain.usecases.GetAnnouncementUseCaseImpl
import dsm.service.announcement.domain.usecases.UpdateAnnouncementUseCaseImpl
import dsm.service.announcement.infrastructure.repositories.AnnouncementRepositoryImpl
import dsm.service.announcement.proto.*

class AnnouncementServicer(
    private val announcementRepository: AnnouncementRepository = AnnouncementRepositoryImpl(),
    private val announcementService: AnnouncementService = AnnouncementServiceImpl(
        announcementMapper = AnnouncementMapper(),
        getAnnouncementUseCase = GetAnnouncementUseCaseImpl(
            announcementRepository
        ),
        createAnnouncementUseCase = CreateAnnouncementUseCaseImpl(
            announcementRepository
        ),
        updateAnnouncementUseCase = UpdateAnnouncementUseCaseImpl(
            announcementRepository
        )
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
        return super.updateAnnouncement(request)
    }

    override suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DeleteAnnouncementResponse {
        return super.deleteAnnouncement(request)
    }
}