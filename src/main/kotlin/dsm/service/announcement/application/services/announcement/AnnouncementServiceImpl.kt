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
import dsm.service.announcement.proto.CreateAnnouncementRequest
import dsm.service.announcement.proto.CreateAnnouncementResponse
import dsm.service.announcement.proto.GetAnnouncementRequest
import dsm.service.announcement.proto.GetAnnouncementResponse
import jdk.nashorn.internal.parser.JSONParser
import org.json.JSONObject

class   AnnouncementServiceImpl(
    val announcementMapper: AnnouncementMapper = AnnouncementMapper(),
    val getAnnouncementUseCase: GetAnnouncementUseCase = GetAnnouncementUseCaseImpl(),
    val createAnnouncementUseCase: CreateAnnouncementUseCase = CreateAnnouncementUseCaseImpl(
        AnnouncementRepositoryImpl()
    )
) : AnnouncementService {

    override fun getAnnouncement(getAnnouncementRequest: GetAnnouncementRequest): GetAnnouncementResponse {
        val a = AnnouncementRepositoryImpl().findByType("hello")
        return GetAnnouncementResponse.newBuilder()
            .build()
    }

    override fun createAnnouncement(createAnnouncementRequest: CreateAnnouncementRequest): CreateAnnouncementResponse {
        val contentUuid: String = createAnnouncementUseCase.createContent(
            createAnnouncementRequest.content
        )

        createAnnouncementUseCase.createAnnouncementUseCase(
            announcementMapper.createAnnouncementRequestMapper(
                createAnnouncementRequest, contentUuid))

        return CreateAnnouncementResponse.newBuilder()
            .build()
    }
}