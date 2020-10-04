package dsm.service.announcement.infrastructure.auth

import dsm.service.announcement.proto.AuthStudentGrpcKt
import dsm.service.announcement.proto.GetStudentInformWithUUIDRequest
import dsm.service.announcement.proto.GetStudentInformWithUUIDResponse
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.stub.MetadataUtils

class AuthManager(
    val host: String = "127.0.0.1",
    val port: Int = 10071,
    val channel: ManagedChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build(),
    val stub: AuthStudentGrpcKt.AuthStudentCoroutineStub = AuthStudentGrpcKt.AuthStudentCoroutineStub(channel),
    val metadata: Metadata = Metadata()
) {
    suspend fun getStudentInformation(xRequestId: String, spanContext: String, studentUUID: String): GetStudentInformWithUUIDResponse {
        metadata.put(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER), xRequestId)
        metadata.put(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER), spanContext)

        val request = GetStudentInformWithUUIDRequest.newBuilder()
            .setUUID(studentUUID)
            .setStudentUUID(studentUUID)
            .build()

        return MetadataUtils.attachHeaders(stub, metadata).getStudentInformWithUUID(request)
    }
}