package dsm.service.announcement.controller.grpc

import io.grpc.*
import org.lognet.springboot.grpc.GRpcGlobalInterceptor

@GRpcGlobalInterceptor
class MetadataInterceptor: ServerInterceptor {

    companion object {
        val xRequestId: Context.Key<Any> = Context.key("xRequestId")
        val spanContext: Context.Key<Any> = Context.key("spanContext")
    }

    override fun <ReqT: Any, RespT: Any> interceptCall(
            call: ServerCall<ReqT, RespT>,
            headers: Metadata,
            next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        return Context.current()
                .withValues(
                        xRequestId,
                        headers.get(Metadata.Key.of("x-request-id", Metadata.ASCII_STRING_MARSHALLER)),
                        spanContext,
                        headers.get(Metadata.Key.of("span-context", Metadata.ASCII_STRING_MARSHALLER))
                )
                .let {  context ->
                    Contexts.interceptCall(context, call, headers, next)
                }
    }

}