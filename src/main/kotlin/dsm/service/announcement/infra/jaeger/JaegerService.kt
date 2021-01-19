package dsm.service.announcement.infra.jaeger

import dsm.service.announcement.controller.grpc.MetadataInterceptor
import io.jaegertracing.Configuration
import io.jaegertracing.internal.JaegerSpanContext
import io.opentracing.Span
import io.opentracing.SpanContext
import io.opentracing.Tracer
import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component

@Component
class JaegerService {
    private var samplerConfiguration = Configuration.SamplerConfiguration
        .fromEnv()
        .withParam(1)
        .withType("const")

    private val tracer: Tracer = Configuration.fromEnv("DMS.SMS.v1.service.announcement")
        .withSampler(samplerConfiguration)
        .getTracer()

    fun processTracing(pjp: ProceedingJoinPoint): Any {
        val spanContext = MetadataInterceptor.spanContext.get() as String
        val xRequestId = MetadataInterceptor.xRequestId.get() as String
        val span: Span = tracer.buildSpan("service").asChildOf(generateSpanContext(spanContext)).start()
        try {
            tracer.scopeManager().activate(span).use {
                span.setTag("x-request-id", xRequestId)
                return pjp.proceed()
            }
        } finally {
            span.finish()
        }
    }

    fun extensionTracing(pjp: ProceedingJoinPoint, serviceName: String): Any {
        val span = tracer.buildSpan(serviceName).asChildOf(
            tracer.activeSpan()
        ).start()
        var service: Any = Any()
        try {
            tracer.scopeManager().activate(span).use { service = pjp.proceed() }
        } finally {
            span.finish()
        }
        return service
    }

    private fun generateSpanContext(spanContext: String): SpanContext {
        val splitSpanContext = spanContext.split(":").toTypedArray()
        val traceIdLow = java.lang.Long.parseUnsignedLong(splitSpanContext[0], 16)
        val spanId = java.lang.Long.parseUnsignedLong(splitSpanContext[1], 16)
        val parentId = java.lang.Long.parseUnsignedLong(splitSpanContext[2], 16)
        val flags = java.lang.Byte.valueOf(splitSpanContext[3], 16)
        return JaegerSpanContext(0, traceIdLow, spanId, parentId, flags)
    }

    fun getActiveSpanString(): String {
        return tracer.activeSpan().toString().split(" ")[0]
    }

    fun tracingStart(serviceName: String?) {
        val span = tracer.buildSpan(serviceName).asChildOf(
            tracer.activeSpan()
        ).start()
        tracer.scopeManager().activate(span)
    }

    fun tracingEnd() {
        val span = tracer.activeSpan()
        span.finish()
    }
}