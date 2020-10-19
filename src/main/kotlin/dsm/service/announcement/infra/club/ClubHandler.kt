package dsm.service.announcement.infra.club

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
    val host: String = "127.0.0.1"
    val serviceName: String = "DMS.SMS.v1.service.club"

    @Tracing("ClubServiceHandler")
    suspend fun getClubUuidWithLeaderUuid(
            uuid: String,
            xRequestId: String
    ): GetClubUUIDWithLeaderUUIDResponse {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
//                consulHandler.getServiceHost(serviceName),
                host,
                consulHandler.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: ClubStudentGrpcKt.ClubStudentCoroutineStub = ClubStudentGrpcKt.ClubStudentCoroutineStub(channel)

        val metadata: Metadata = Metadata()
        val spanContext = jaegerHandler.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetClubUUIDWithLeaderUUIDRequest.newBuilder()
                .setUUID(uuid)
                .setLeaderUUID(uuid)
                .build()

        return try {
            MetadataUtils.attachHeaders(stub, metadata).getClubUUIDWithLeaderUUID(request)
        } catch (e: Exception) {
            GetClubUUIDWithLeaderUUIDResponse.newBuilder().build()
        }
    }

    @Tracing("ClubServiceHandler")
    suspend fun getClubWithClubUuid(
            accountUuid: String,
            clubUuid: String,
            xRequestId: String
    ): GetClubInformWithUUIDResponse {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
//                consulHandler.getServiceHost(serviceName),
                host,
                consulHandler.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: ClubStudentGrpcKt.ClubStudentCoroutineStub = ClubStudentGrpcKt.ClubStudentCoroutineStub(channel)

        val metadata: Metadata = Metadata()
        val spanContext = jaegerHandler.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetClubInformWithUUIDRequest.newBuilder()
                .setUUID(accountUuid)
                .setClubUUID(clubUuid)
                .build()

        return try {
            MetadataUtils.attachHeaders(stub, metadata).getClubInformWithUUID(request)
        } catch (e: Exception) {
            GetClubInformWithUUIDResponse.newBuilder().build()
        }
    }
}