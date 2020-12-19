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
        val getAccountUseCase: GetAccountUseCase,
        val checkAnnouncementUseCase: CheckAnnouncementUseCase,
        val searchAnnouncementsUseCase: SearchAnnouncementsUseCase,
        val getTeacherAnnouncementsUseCase: GetTeacherAnnouncementsUseCase,

        val announcementMapper: AnnouncementMapper
): AnnouncementService {
    override fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse {
        val announcementId = createAnnouncementUseCase.execute(
            request.uuid,
            request.title,
            request.content,
            request.targetGrade,
            request.targetGroup,
            request.type)
        return DefaultAnnouncementResponse.newBuilder()
                .setAnnouncementId(announcementId)
                .setStatus(201)
                .build();
    }

    override fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse {
        deleteAnnouncementUseCase.execute(request.uuid, request.announcementId)
        return DefaultAnnouncementResponse.newBuilder()
                .setAnnouncementId(request.announcementId)
                .setStatus(201)
                .build();
    }

    override fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        val currentAnnouncement = getAnnouncementDetailUseCase.execute(request.announcementId, request.uuid)
        return announcementMapper.getAnnouncementDetailMapper(
                currentAnnouncement,
                getContentUseCase.execute(request.announcementId),
                getNextAnnouncementUseCase.execute(currentAnnouncement),
                getPreviewAnnouncementUseCase.execute(currentAnnouncement)

        ).setStatus(200).build()
    }

    override fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse {
        val announcement = getAnnouncementsUseCase.execute(request.uuid, request.type, request.start, request.count)

        return announcementMapper.getAnnouncementsMapper(
                announcement,
                request.uuid
        ).setStatus(200).build()
    }

    override fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse {
        updateAnnouncementUseCase.execute(
                request.uuid,
                request.announcementId,
                request.title,
                request.content,
                request.targetGrade,
                request.targetGroup)
        return DefaultAnnouncementResponse.newBuilder()
                .setAnnouncementId(request.announcementId)
                .setStatus(201)
                .build();
    }

    override fun checkAnnouncement(request: CheckAnnouncementRequest): CheckAnnouncementResponse {
        return CheckAnnouncementResponse.newBuilder()
                .setClub(checkAnnouncementUseCase.execute(request.uuid, "club"))
                .setSchool(checkAnnouncementUseCase.execute(request.uuid, "school"))
                .setStatus(200)
                .build()
    }

    override fun searchAnnouncements(request: SearchAnnouncementsRequest): GetAnnouncementsResponse {
        val announcements = searchAnnouncementsUseCase.execute(request.uuid, request.query, request.type, request.start, request.count)

        return announcementMapper.getAnnouncementsMapper(
                announcements,
                request.uuid
        ).setStatus(200).build()
    }

    override fun getMyAnnouncements(request: GetMyAnnouncementsRequest): GetAnnouncementsResponse {
        val announcements = getTeacherAnnouncementsUseCase.execute(request.uuid, request.start, request.count)

        return announcementMapper.getAnnouncementsMapper(
                announcements,
                request.uuid
        ).setStatus(200).build()
    }
}