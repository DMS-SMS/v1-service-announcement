package dsm.service.announcement.grpc

import dsm.service.announcement.proto.*
import dsm.service.announcement.service.AnnouncementService
import io.grpc.Context
import io.grpc.Contexts
import org.lognet.springboot.grpc.GRpcService
import org.springframework.data.mongodb.repository.Meta

@GRpcService
public class AnnouncementServiceGrpc(
        val announcementService: AnnouncementService
): AnnouncementServiceGrpcKt.AnnouncementServiceCoroutineImplBase() {
    override suspend fun createAnnouncement(request: CreateAnnouncementRequest): DefaultAnnouncementResponse {
        return announcementService.createAnnouncement(
                request,
                MetadataInterceptor.xRequestId.get() as String,
                MetadataInterceptor.spanContext.get() as String)
    }

    override suspend fun deleteAnnouncement(request: DeleteAnnouncementRequest): DefaultAnnouncementResponse {
        return announcementService.deleteAnnouncement(
                request,
                MetadataInterceptor.xRequestId.get() as String,
                MetadataInterceptor.spanContext.get() as String)
    }

    override suspend fun getAnnouncementDetail(request: GetAnnouncementDetailRequest): GetAnnouncementDetailResponse {
        return announcementService.getAnnouncementDetail(
                request,
                MetadataInterceptor.xRequestId.get() as String,
                MetadataInterceptor.spanContext.get() as String)
    }

    override suspend fun getAnnouncements(request: GetAnnouncementsRequest): GetAnnouncementsResponse {
        println(MetadataInterceptor.xRequestId.get() as String)
        return announcementService.getAnnouncements(
                request,
                MetadataInterceptor.xRequestId.get() as String,
                MetadataInterceptor.spanContext.get() as String)
    }

    override suspend fun updateAnnouncement(request: UpdateAnnouncementRequest): DefaultAnnouncementResponse {
        return announcementService.updateAnnouncement(
                request,
                MetadataInterceptor.xRequestId.get() as String,
                MetadataInterceptor.spanContext.get() as String)
    }
}