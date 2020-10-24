package dsm.service.announcement.service

import dsm.service.announcement.domain.usecase.*
import dsm.service.announcement.proto.*
import dsm.service.announcement.service.mapper.AnnouncementMapper
import org.springframework.stereotype.Component


@Component
class AnnouncementServiceImpl(
        val createAnnouncementUseCase: CreateAnnouncementUseCase,
        val getAnnouncementsUseCase: GetAnnouncementsUseCase,
        val getContentUseCase: GetContentUseCase,
        val getAnnouncementDetailUseCase: GetAnnouncementDetailUseCase,
        val deleteAnnouncementUseCase: DeleteAnnouncementUseCase,
        val updateAnnouncementUseCase: UpdateAnnouncementUseCase,
        val getNextAnnouncementUseCase: GetNextAnnouncementUseCase,
        val getPreviewAnnouncementUseCase: GetPreviousAnnouncementUseCase,

        val announcementMapper: AnnouncementMapper
): AnnouncementService {
    override fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse {
        createAnnouncementUseCase.run(
            request.uuid,
            request.title,
            request.content,
            request.targetGrade,
            request.targetGroup,
            request.type)
        return DefaultAnnouncementResponse.newBuilder().setStatus(201).build();
    }

    override fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse {
        deleteAnnouncementUseCase.run(request.uuid, request.announcementId)
        return DefaultAnnouncementResponse.newBuilder().setStatus(201).build();
    }

    override fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        val currentAnnouncement = getAnnouncementDetailUseCase.run(request.announcementId, request.uuid)
        return announcementMapper.getAnnouncementDetailMapper(
                currentAnnouncement,
                getContentUseCase.run(request.announcementId),
                getNextAnnouncementUseCase.run(currentAnnouncement),
                getPreviewAnnouncementUseCase.run(currentAnnouncement)

        ).setStatus(200).build()
    }

    override fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse {
        return announcementMapper.getAnnouncementsMapper(
                getAnnouncementsUseCase.run(request.uuid, request.type)
        ).setStatus(200).build()
    }

    override fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse {
        updateAnnouncementUseCase.run(
                request.uuid,
                request.announcementId,
                request.title,
                request.content,
                request.targetGrade,
                request.targetGroup)
        return DefaultAnnouncementResponse.newBuilder().setStatus(201).build();
    }
}