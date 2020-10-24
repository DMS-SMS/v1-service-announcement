package dsm.service.announcement.infra.auth

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
class AuthHandler(
        val jaegerHandler: JaegerHandler,
        val consulHandler: ConsulHandler
) {
    val host: String = "127.0.0.1"
    val serviceName: String = "DMS.SMS.v1.service.auth"

    @Tracing("AuthServiceHandler")
    suspend fun getStudentInform(uuid: String): GetStudentInformWithUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
//                consulHandler.getServiceHost(serviceName),
                host,
                consulHandler.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: AuthStudentGrpcKt.AuthStudentCoroutineStub = AuthStudentGrpcKt.AuthStudentCoroutineStub(channel)


        val metadata: Metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerHandler.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetStudentInformWithUUIDRequest.newBuilder()
                .setUUID(uuid)
                .setStudentUUID(uuid)
                .build()

        val response = MetadataUtils.attachHeaders(stub, metadata).getStudentInformWithUUID(request)
        if (response.status != 200) return null

        return try {
            response
        } catch (e: Exception) {
            return null
        }
    }

    @Tracing("AuthServiceHandler")
    suspend fun getTeacherInform(uuid: String): GetTeacherInformWithUUIDResponse? {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
//                consulHandler.getServiceHost(serviceName),
                host,
                consulHandler.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: AuthTeacherGrpcKt.AuthTeacherCoroutineStub = AuthTeacherGrpcKt.AuthTeacherCoroutineStub(channel)


        val metadata: Metadata = Metadata()
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val spanContext = jaegerHandler.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetTeacherInformWithUUIDRequest.newBuilder()
                .setUUID(uuid)
                .setTeacherUUID(uuid)
                .build()
        val response = MetadataUtils.attachHeaders(stub, metadata).getTeacherInformWithUUID(request)
        if (response.status != 200) return null

        return try {
            response
        } catch (e: Exception) {
            return null
        }
    }
}