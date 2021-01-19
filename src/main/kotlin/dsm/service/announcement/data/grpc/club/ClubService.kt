package dsm.service.announcement.data.grpc.club

import dsm.service.announcement.controller.grpc.MetadataInterceptor
import dsm.service.announcement.infra.aop.annotation.Tracing
import dsm.service.announcement.infra.consul.ConsulService
import dsm.service.announcement.infra.jaeger.JaegerService
import dsm.service.announcement.proto.*
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.Metadata.ASCII_STRING_MARSHALLER
import io.grpc.stub.MetadataUtils
import org.springframework.stereotype.Component

@Component
class ClubService(
    val consulService: ConsulService,
    val jaegerService: JaegerService
) {
    val serviceName: String = "DMS.SMS.v1.service.club"

    @Tracing("getClubUuidWithLeaderUuid Channel")
    suspend fun getClubUuidWithLeaderUuid(uuid: String): GetClubUUIDWithLeaderUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
            consulService.getServiceHost(serviceName),
            consulService.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: ClubStudentGrpcKt.ClubStudentCoroutineStub = ClubStudentGrpcKt.ClubStudentCoroutineStub(channel)

        val metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerService.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", ASCII_STRING_MARSHALLER), spanContext)

        val request = GetClubUUIDWithLeaderUUIDRequest.newBuilder()
            .setUUID(uuid)
            .setLeaderUUID(uuid)
            .build()


        val response = MetadataUtils.attachHeaders(stub, metadata).getClubUUIDWithLeaderUUID(request)
        channel.shutdown()

        return if (response.status != 200) { null } else { response }
    }

    @Tracing("getClubWithClubUuid Channel")
    suspend fun getClubWithClubUuid(accountUuid: String, clubUuid: String): GetClubInformWithUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
            consulService.getServiceHost(serviceName),
            consulService.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: ClubStudentGrpcKt.ClubStudentCoroutineStub = ClubStudentGrpcKt.ClubStudentCoroutineStub(channel)

        val metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerService.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", ASCII_STRING_MARSHALLER), spanContext)

        val request = GetClubInformWithUUIDRequest.newBuilder()
            .setUUID(accountUuid)
            .setClubUUID(clubUuid)
            .build()


        val response = MetadataUtils.attachHeaders(stub, metadata).getClubInformWithUUID(request)
        channel.shutdown()

        return if (response.status != 200) { null } else { response }
    }
}