package dsm.service.announcement.v1.service.aop

import dsm.service.announcement.v1.domain.exception.BusinessException
import dsm.service.announcement.v1.infra.jaeger.JaegerHandler
import dsm.service.announcement.proto.CheckAnnouncementResponse
import dsm.service.announcement.proto.DefaultAnnouncementResponse
import dsm.service.announcement.proto.GetAnnouncementDetailResponse
import dsm.service.announcement.proto.GetAnnouncementsResponse
import dsm.service.announcement.v1.service.aop.annotation.Tracing
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component


@Component
@Aspect
public class AspectService(
        val jaegerHandler: JaegerHandler
) {
    @Pointcut("execution(* dsm.service.announcement.service.AnnouncementServiceImpl.createAnnouncement(..)) || " +
            "execution(* dsm.service.announcement.service.AnnouncementServiceImpl.updateAnnouncement(..)) || " +
            "execution(* dsm.service.announcement.service.AnnouncementServiceImpl.deleteAnnouncement(..))")
    fun defaultAnnouncementPointCut() {}

    @Pointcut("execution(* dsm.service.announcement.service.AnnouncementServiceImpl.getAnnouncements(..)) ||" +
            "execution(* dsm.service.announcement.service.AnnouncementServiceImpl.searchAnnouncements(..))")
    fun getAnnouncementsPointCut() {}

    @Pointcut("execution(* dsm.service.announcement.service.AnnouncementServiceImpl.getAnnouncementDetail(..))")
    fun getAnnouncementDetailPointCut() {}

    @Pointcut("execution(* dsm.service.announcement.service.AnnouncementServiceImpl.checkAnnouncement(..))")
    fun checkAnnouncementPointCut() {}

    @Around("getAnnouncementsPointCut()")
    fun getAnnouncementsHandling(pjp: ProceedingJoinPoint): Any {
        return try {
            jaegerHandler.serviceTracing(pjp)
        } catch (e: BusinessException) {
            GetAnnouncementsResponse.newBuilder()
                    .setStatus(e.statusCode)
                    .setCode(e.errorCode)
                    .setMsg(e.message)
                    .build()
        } catch (e: Exception) {
            println(e)
        }
    }


    @Around("getAnnouncementDetailPointCut()")
    fun getAnnouncementDetailHandling(pjp: ProceedingJoinPoint): Any {
        return try {
            jaegerHandler.serviceTracing(pjp)
        } catch (e: BusinessException) {
            GetAnnouncementDetailResponse.newBuilder()
                    .setStatus(e.statusCode)
                    .setCode(e.errorCode)
                    .setMsg(e.message)
                    .build()
        } catch (e: BusinessException) {
            println(e.message)
        } catch (e: Exception) {
            println(e)
        }
    }

    @Around("defaultAnnouncementPointCut()")
    fun defaultAnnouncementHandling(pjp: ProceedingJoinPoint): Any {
        return try {
            jaegerHandler.serviceTracing(pjp)
        } catch (e: BusinessException) {
            DefaultAnnouncementResponse.newBuilder()
                    .setStatus(e.statusCode)
                    .setCode(e.errorCode)
                    .setMsg(e.message)
                    .build()
        } catch (e: Exception) {
            println(e)
        }
    }

    @Around("checkAnnouncementPointCut()")
    fun checkAnnouncementHandling(pjp: ProceedingJoinPoint): Any {
        return try {
            jaegerHandler.serviceTracing(pjp)
        } catch (e: BusinessException) {
            CheckAnnouncementResponse.newBuilder()
                    .setStatus(e.statusCode)
                    .setCode(e.errorCode)
                    .setMsg(e.message)
                    .build()
        } catch (e: Exception) {
            println(e)
        }
    }

    @Around("@annotation(dsm.service.announcement.service.aop.annotation.Tracing) && @annotation(tracing)")
    fun extensionTracing(pjp: ProceedingJoinPoint, tracing: Tracing): Any {
        return jaegerHandler.extensionTracing(pjp, tracing.serviceName)
    }
}