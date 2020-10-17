package dsm.service.announcement.service.aop.annotation

@Retention(AnnotationRetention.RUNTIME)
annotation class Tracing(val serviceName: String = "")