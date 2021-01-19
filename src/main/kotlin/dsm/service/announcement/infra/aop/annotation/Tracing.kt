package dsm.service.announcement.infra.aop.annotation

@Retention(AnnotationRetention.RUNTIME)
annotation class Tracing(val serviceName: String = "")