package dsm.service.announcement.infra.aop

import dsm.service.announcement.core.domain.exception.BusinessException
import dsm.service.announcement.infra.aop.annotation.Tracing
import dsm.service.announcement.infra.jaeger.JaegerService
import dsm.service.announcement.proto.CheckAnnouncementResponse
import dsm.service.announcement.proto.DefaultAnnouncementResponse
import dsm.service.announcement.proto.GetAnnouncementDetailResponse
import dsm.service.announcement.proto.GetAnnouncementsResponse
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Component
@Aspect
class AspectJService(
    val jaegerService: JaegerService
) {
    @Pointcut("execution(* dsm.service.announcement.controller.grpc.AnnouncementServiceGrpc.createAnnouncement(..)) || " +
            "execution(* dsm.service.announcement.controller.grpc.AnnouncementServiceGrpc.updateAnnouncement(..)) || " +
            "execution(* dsm.service.announcement.controller.grpc.AnnouncementServiceGrpc.deleteAnnouncement(..))")
    fun defaultAnnouncementPointCut() {}

    @Pointcut("execution(* dsm.service.announcement.controller.grpc.AnnouncementServiceGrpc.getAnnouncements(..)) ||" +
            "execution(* dsm.service.announcement.controller.grpc.AnnouncementServiceGrpc.getMyAnnouncements(..)) ||" +
            "execution(* dsm.service.announcement.controller.grpc.AnnouncementServiceGrpc.searchAnnouncements(..))")
    fun getAnnouncementsPointCut() {}

    @Pointcut("execution(* dsm.service.announcement.controller.grpc.AnnouncementServiceGrpc.getAnnouncementDetail(..))")
    fun getAnnouncementDetailPointCut() {}

    @Pointcut("execution(* dsm.service.announcement.controller.grpc.AnnouncementServiceGrpc.checkAnnouncement(..))")
    fun checkAnnouncementPointCut() {}

    @Around("getAnnouncementsPointCut()")
    fun getAnnouncementsHandling(pjp: ProceedingJoinPoint): Any {
        return try {
            jaegerService.processTracing(pjp)
        } catch (e: BusinessException) {
            GetAnnouncementsResponse.newBuilder()
                .setStatus(e.statusCode)
                .setCode(e.errorCode)
                .setMsg(e.message)
                .build()
        } catch (e: Exception) {
            GetAnnouncementsResponse.newBuilder()
                .setStatus(500)
                .setMsg(e.message)
                .build()
        }
    }


    @Around("getAnnouncementDetailPointCut()")
    fun getAnnouncementDetailHandling(pjp: ProceedingJoinPoint): Any {
        return try {
            jaegerService.processTracing(pjp)
        } catch (e: BusinessException) {
            GetAnnouncementDetailResponse.newBuilder()
                .setStatus(e.statusCode)
                .setCode(e.errorCode)
                .setMsg(e.message)
                .build()
       } catch (e: Exception) {
            GetAnnouncementDetailResponse.newBuilder()
                .setStatus(500)
                .setMsg(e.message)
                .build()
        }
    }

    @Around("defaultAnnouncementPointCut()")
    fun defaultAnnouncementHandling(pjp: ProceedingJoinPoint): Any {
        return try {
            jaegerService.processTracing(pjp)
        } catch (e: BusinessException) {
            DefaultAnnouncementResponse.newBuilder()
                .setStatus(e.statusCode)
                .setCode(e.errorCode)
                .setMsg(e.message)
                .build()
        } catch (e: Exception) {
            DefaultAnnouncementResponse.newBuilder()
                .setStatus(500)
                .setMsg(e.message)
                .build()
        }
    }

    @Around("checkAnnouncementPointCut()")
    fun checkAnnouncementHandling(pjp: ProceedingJoinPoint): Any {
        return try {
            jaegerService.processTracing(pjp)
        } catch (e: BusinessException) {
            CheckAnnouncementResponse.newBuilder()
                .setStatus(e.statusCode)
                .setCode(e.errorCode)
                .setMsg(e.message)
                .build()
        } catch (e: Exception) {
            CheckAnnouncementResponse.newBuilder()
                .setStatus(500)
                .setMsg(e.message)
                .build()
        }
    }

    @Around("@annotation(dsm.service.announcement.infra.aop.annotation.Tracing) && @annotation(tracing)")
    fun extensionTracing(pjp: ProceedingJoinPoint, tracing: Tracing): Any {
        return jaegerService.extensionTracing(pjp, tracing.serviceName)
    }
}