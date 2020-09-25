package dsm.service.announcement.application.services.announcement

import com.google.gson.JsonObject
import dsm.service.announcement.application.mapper.AnnouncementMapper
import dsm.service.announcement.domain.entities.Announcement
import dsm.service.announcement.domain.repositories.AnnouncementRepository
import dsm.service.announcement.domain.usecases.CreateAnnouncementUseCase
import dsm.service.announcement.domain.usecases.CreateAnnouncementUseCaseImpl
import dsm.service.announcement.domain.usecases.GetAnnouncementUseCase
import dsm.service.announcement.domain.usecases.GetAnnouncementUseCaseImpl
import dsm.service.announcement.infrastructure.repositories.AnnouncementRepositoryImpl
import dsm.service.announcement.proto.*
import jdk.nashorn.internal.parser.JSONParser
import org.json.JSONObject

class AnnouncementServiceImpl(
    val announcementMapper: AnnouncementMapper,
    val getAnnouncementUseCase: GetAnnouncementUseCase,
    val createAnnouncementUseCase: CreateAnnouncementUseCase
) : AnnouncementService {
    override fun getAnnouncement(getAnnouncementRequest: GetAnnouncementRequest): GetAnnouncementResponse {
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
        val contentUuid: String = createAnnouncementUseCase.createContent(
            createAnnouncementRequest.content
        )

        createAnnouncementUseCase.createAnnouncement(
            announcementMapper.createAnnouncementRequestMapper(
                createAnnouncementRequest, contentUuid))

        return CreateAnnouncementResponse.newBuilder()
            .build()
    }
}