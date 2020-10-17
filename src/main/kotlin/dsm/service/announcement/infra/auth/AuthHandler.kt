package dsm.service.announcement.infra.auth

import dsm.service.announcement.infra.consul.ConsulHandler
import dsm.service.announcement.infra.jaeger.JaegerHandler
import dsm.service.announcement.proto.AuthStudentGrpcKt
import dsm.service.announcement.proto.GetStudentInformWithUUIDRequest
import dsm.service.announcement.proto.GetStudentInformWithUUIDResponse
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
    suspend fun getStudentInform(
            uuid: String,
            xRequestId: String
    ): GetStudentInformWithUUIDResponse {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(
//                consulHandler.getServiceHost(serviceName),
                host,
                consulHandler.getServicePort(serviceName)
        ).usePlaintext().build()
        val stub: AuthStudentGrpcKt.AuthStudentCoroutineStub = AuthStudentGrpcKt.AuthStudentCoroutineStub(channel)


        val metadata: Metadata = Metadata()
        val spanContext = jaegerHandler.getActiveSpanString()

        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetStudentInformWithUUIDRequest.newBuilder()
                .setUUID(uuid)
                .setStudentUUID(uuid)
                .build()

        return try {
            MetadataUtils.attachHeaders(stub, metadata).getStudentInformWithUUID(request)
        } catch (e: Exception) {
            GetStudentInformWithUUIDResponse.newBuilder().build()
        }
    }
}