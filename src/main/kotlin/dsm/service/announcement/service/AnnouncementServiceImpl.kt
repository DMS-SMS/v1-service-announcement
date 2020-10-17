package dsm.service.announcement.service

import dsm.service.announcement.domain.usecase.*
import dsm.service.announcement.proto.*
import dsm.service.announcement.service.mapper.AnnouncementMapper
import org.springframework.stereotype.Component
import java.lang.Exception


@Component
class AnnouncementServiceImpl(
        val createAnnouncementUseCase: CreateAnnouncementUseCase,
        val getAnnouncementsUseCase: GetAnnouncementsUseCase,
        val getContentUseCase: GetContentUseCase,
        val getAnnouncementDetailUseCase: GetAnnouncementDetailUseCase,
        val deleteAnnouncementUseCase: DeleteAnnouncementUseCase,
        val updateAnnouncementUseCase: UpdateAnnouncementUseCase,

        val announcementMapper: AnnouncementMapper
): AnnouncementService {
    override fun createAnnouncement(request: CreateAnnouncementRequest, xRequestId: String, spanContext: String): DefaultAnnouncementResponse {
        createAnnouncementUseCase.run(
            request.uuid,
            request.title,
            request.content,
            request.targetGrade,
            request.targetGroup,
            request.type)
        return DefaultAnnouncementResponse.newBuilder().setStatus(201).build();
    }

    override fun deleteAnnouncement(request: DeleteAnnouncementRequest, xRequestId: String, spanContext: String): DefaultAnnouncementResponse {
        deleteAnnouncementUseCase.run(request.uuid, request.announcementId)
        return DefaultAnnouncementResponse.newBuilder().setStatus(201).build();
    }

    override fun getAnnouncementDetail(request: GetAnnouncementDetailRequest, xRequestId: String, spanContext: String): GetAnnouncementDetailResponse {
        return announcementMapper.getAnnouncementDetailMapper(
                getAnnouncementDetailUseCase.run(request.announcementId),
                getContentUseCase.run(request.announcementId)).setStatus(200).build()
    }

    override fun getAnnouncements(request: GetAnnouncementsRequest, xRequestId: String, spanContext: String): GetAnnouncementsResponse {
        return announcementMapper.getAnnouncementsMapper(
                getAnnouncementsUseCase.run(request.uuid, request.type, xRequestId)
        ).setStatus(200).build()
    }

    override fun updateAnnouncement(request: UpdateAnnouncementRequest, xRequestId: String, spanContext: String): DefaultAnnouncementResponse {
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