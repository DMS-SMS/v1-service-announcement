package dsm.service.announcement.data.grpc.auth

import dsm.service.announcement.controller.grpc.MetadataInterceptor
import dsm.service.announcement.infra.consul.ConsulService
import dsm.service.announcement.infra.jaeger.JaegerService
import dsm.service.announcement.proto.*
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.stub.MetadataUtils
import org.springframework.stereotype.Component

@Component
class AuthService(
    val consulService: ConsulService,
    val jaegerService: JaegerService
) {
    val serviceName = "DMS.SMS.v1.service.auth"

    suspend fun getStudentInform(studentUuid: String, accountUuid: String): GetStudentInformWithUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
            consulService.getServiceHost(serviceName),
            consulService.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: AuthStudentGrpcKt.AuthStudentCoroutineStub = AuthStudentGrpcKt.AuthStudentCoroutineStub(channel)


        val metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerService.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetStudentInformWithUUIDRequest.newBuilder()
            .setUUID(accountUuid)
            .setStudentUUID(studentUuid)
            .build()

        val response = MetadataUtils.attachHeaders(stub, metadata).getStudentInformWithUUID(request)
        channel.shutdown()

        return if (response.status != 200) { null } else { response }
    }

    suspend fun getTeacherInform(teacherUuid: String, accountUuid: String): GetTeacherInformWithUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
            consulService.getServiceHost(serviceName),
            consulService.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: AuthTeacherGrpcKt.AuthTeacherCoroutineStub = AuthTeacherGrpcKt.AuthTeacherCoroutineStub(channel)


        val metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerService.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetTeacherInformWithUUIDRequest.newBuilder()
            .setUUID(accountUuid)
            .setTeacherUUID(teacherUuid)
            .build()

        val response = MetadataUtils.attachHeaders(stub, metadata).getTeacherInformWithUUID(request)
        channel.shutdown()

        return if (response.status != 200) { null } else { response }
    }

    suspend fun getParentInform(parentUuid: String, accountUuid: String): GetParentInformWithUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
            consulService.getServiceHost(serviceName),
            consulService.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub = AuthParentGrpcKt.AuthParentCoroutineStub(channel)


        val metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerService.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetParentInformWithUUIDRequest.newBuilder()
            .setUUID(accountUuid)
            .setParentUUID(parentUuid)
            .build()

        val response = MetadataUtils.attachHeaders(stub, metadata).getParentInformWithUUID(request)
        channel.shutdown()

        return if (response.status != 200) { null } else { response }
    }
}