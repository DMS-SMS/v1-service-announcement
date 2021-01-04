package dsm.service.announcement.v1.service.aop.annotation

@Retention(AnnotationRetention.RUNTIME)
annotation class Tracing(val serviceName: String = "")