package dsm.service.announcement.application.aop

import com.google.protobuf.MessageLite
import dsm.service.announcement.application.services.announcement.AnnouncementService
import dsm.service.announcement.domain.exceptions.BusinessException
import dsm.service.announcement.proto.*
import io.grpc.stub.StreamObserver


class AnnouncementServiceProxy(
    val announcementService: AnnouncementService
): AnnouncementService{
    override fun createAnnouncement(createAnnouncementRequest: CreateAnnouncementRequest): CreateAnnouncementResponse {
        return try { announcementService.createAnnouncement(createAnnouncementRequest) }
        catch (e: BusinessException) {
            CreateAnnouncementResponse
                .newBuilder().setCode(e.code).setStatus(e.status).setMsg(e.message).build()
        }
    }

    override fun deleteAnnouncement(deleteAnnouncementRequest: DeleteAnnouncementRequest): DeleteAnnouncementResponse {
        return try { announcementService.deleteAnnouncement(deleteAnnouncementRequest) }
        catch (e: BusinessException) {
            DeleteAnnouncementResponse
                .newBuilder().setCode(e.code).setStatus(e.status).setMsg(e.message).build()
        }
    }

    override fun getAnnouncement(getAnnouncementRequest: GetAnnouncementRequest): GetAnnouncementResponse {
        return try { announcementService.getAnnouncement(getAnnouncementRequest) }
        catch (e: BusinessException) {
            GetAnnouncementResponse
                .newBuilder().setCode(e.code).setStatus(e.status).setMsg(e.message).build()
        }
    }

    override fun getAnnouncementDetail(getAnnouncementDetailRequest: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        return try { announcementService.getAnnouncementDetail(getAnnouncementDetailRequest) }
        catch (e: BusinessException) {
            GetAnnouncementDetailResponse
                .newBuilder().setCode(e.code).setStatus(e.status).setMsg(e.message).build()
        }
    }

    override fun updateAnnouncement(updateAnnouncementRequest: UpdateAnnouncementRequest): UpdateAnnouncementResponse {
        return try { announcementService.updateAnnouncement(updateAnnouncementRequest) }
        catch (e: BusinessException) {
            UpdateAnnouncementResponse
                .newBuilder().setCode(e.code).setStatus(e.status).setMsg(e.message).build()
        }
    }
}