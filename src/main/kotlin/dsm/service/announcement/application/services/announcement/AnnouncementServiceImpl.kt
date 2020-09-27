package dsm.service.announcement.application.services.announcement

import com.google.gson.JsonObject
import dsm.service.announcement.application.mapper.AnnouncementMapper
import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.domain.usecases.*
import dsm.service.announcement.infrastructure.repositories.AnnouncementRepositoryImpl
import dsm.service.announcement.proto.*
import jdk.nashorn.internal.parser.JSONParser
import org.json.JSONObject

open class AnnouncementServiceImpl(
    val announcementMapper: AnnouncementMapper,
    val getAnnouncementUseCase: GetAnnouncementUseCase,
    val createAnnouncementUseCase: CreateAnnouncementUseCase,
    val updateAnnouncementUseCase: UpdateAnnouncementUseCase,
    val deleteAnnouncementUseCase: DeleteAnnouncementUseCase,
    val validateUseCase: ValidateUseCase
) : AnnouncementService {
    open override fun getAnnouncement(getAnnouncementRequest: GetAnnouncementRequest): GetAnnouncementResponse {
        val announcements: List<Announcement?> = getAnnouncementUseCase.getAnnouncements(
            getAnnouncementRequest.uuid, getAnnouncementRequest.type)

        return announcementMapper.getAnnouncementsResponseMapper(announcements)
    }

    override fun getAnnouncementDetail(getAnnouncementDetailRequest: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        val aid = getAnnouncementDetailRequest.aid
        val announcement = getAnnouncementUseCase.getAnnouncement(aid)
        val content = announcement?.contentUuid?.let {
            getAnnouncementUseCase.getAnnouncementContent(it) }

        return announcementMapper.getAnnouncementResponseMapper(announcement, content)
    }

    override fun createAnnouncement(createAnnouncementRequest: CreateAnnouncementRequest): CreateAnnouncementResponse {
        validateUseCase.validateAuth(createAnnouncementRequest.uuid, createAnnouncementRequest.type)

        val contentUuid: String = createAnnouncementUseCase.createContent(
            createAnnouncementRequest.content
        )

        createAnnouncementUseCase.createAnnouncement(
            announcementMapper.createAnnouncementRequestMapper(
                createAnnouncementRequest, contentUuid))

        return CreateAnnouncementResponse.newBuilder()
            .setStatus(201)
            .build()
    }

    override fun updateAnnouncement(updateAnnouncementRequest: UpdateAnnouncementRequest): UpdateAnnouncementResponse {
        validateUseCase.validateAnnouncementAuthority(
            updateAnnouncementRequest.aid, updateAnnouncementRequest.uuid)

        val contentUuid = updateAnnouncementUseCase.updateAnnouncement(
            updateAnnouncementRequest.aid,
            updateAnnouncementRequest.title,
            updateAnnouncementRequest.targetGrade,
            updateAnnouncementRequest.targetClass)

        if (contentUuid != null) {
            updateAnnouncementUseCase.updateContent(
                contentUuid,
                updateAnnouncementRequest.content)
        }

        return UpdateAnnouncementResponse.newBuilder()
            .setStatus(200)
            .build()
    }

    override fun deleteAnnouncement(deleteAnnouncementRequest: DeleteAnnouncementRequest): DeleteAnnouncementResponse {
        validateUseCase.validateAnnouncementAuthority(
            deleteAnnouncementRequest.aid, deleteAnnouncementRequest.uuid
        )

        val contentUuid: String? = deleteAnnouncementUseCase.deleteAnnouncement(
            deleteAnnouncementRequest.aid)

        deleteAnnouncementUseCase.deleteContent(contentUuid)

        return DeleteAnnouncementResponse.newBuilder()
            .setStatus(200)
            .build()
    }
}