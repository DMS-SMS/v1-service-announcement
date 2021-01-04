package dsm.service.announcement.v1.grpc

import io.grpc.*
import org.lognet.springboot.grpc.GRpcGlobalInterceptor


@GRpcGlobalInterceptor
class MetadataInterceptor : ServerInterceptor {
    companion object {
        val xRequestId: Context.Key<Any> = Context.key("xRequestId")
        val spanContext: Context.Key<Any> = Context.key("spanContext")
    }

    override fun <ReqT, RespT> interceptCall(
            call: ServerCall<ReqT, RespT>, headers: Metadata, next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        val context = Context.current().withValues(
                xRequestId, headers.get(
                Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER)),
                spanContext, headers.get(
                Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER)))

        return Contexts.interceptCall(context, call, headers, next)
    }
}