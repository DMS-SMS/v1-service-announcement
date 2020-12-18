package dsm.service.announcement.infra.club

import dsm.service.announcement.grpc.MetadataInterceptor
import dsm.service.announcement.infra.consul.ConsulHandler
import dsm.service.announcement.infra.jaeger.JaegerHandler
import dsm.service.announcement.proto.*
import dsm.service.announcement.service.aop.annotation.Tracing
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.stub.MetadataUtils
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class ClubHandler(
        val jaegerHandler: JaegerHandler,
        val consulHandler: ConsulHandler
) {
    val serviceName: String = "DMS.SMS.v1.service.club"

    @Tracing("ClubServiceHandler")
    suspend fun getClubUuidWithLeaderUuid(uuid: String): GetClubUUIDWithLeaderUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
                consulHandler.getServiceHost(serviceName),
                consulHandler.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: ClubStudentGrpcKt.ClubStudentCoroutineStub = ClubStudentGrpcKt.ClubStudentCoroutineStub(channel)

        val metadata: Metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerHandler.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetClubUUIDWithLeaderUUIDRequest.newBuilder()
                .setUUID(uuid)
                .setLeaderUUID(uuid)
                .build()

        var response:GetClubUUIDWithLeaderUUIDResponse? = null
        try {
            response = MetadataUtils.attachHeaders(stub, metadata).getClubUUIDWithLeaderUUID(request)
            channel.shutdown()
        } catch (e: Exception) {
            channel.shutdown()
        } finally {
            return response
        }
    }

    @Tracing("ClubServiceHandler")
    suspend fun getClubWithClubUuid(accountUuid: String, clubUuid: String): GetClubInformWithUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
                consulHandler.getServiceHost(serviceName),
                consulHandler.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: ClubStudentGrpcKt.ClubStudentCoroutineStub = ClubStudentGrpcKt.ClubStudentCoroutineStub(channel)

        val metadata: Metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerHandler.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetClubInformWithUUIDRequest.newBuilder()
                .setUUID(accountUuid)
                .setClubUUID(clubUuid)
                .build()

        var response: GetClubInformWithUUIDResponse? = null;
        try {
            response = MetadataUtils.attachHeaders(stub, metadata).getClubInformWithUUID(request)
            channel.shutdown()
        } catch (e: Exception) {
            channel.shutdown()
        } finally {
            return response
        }
    }
}