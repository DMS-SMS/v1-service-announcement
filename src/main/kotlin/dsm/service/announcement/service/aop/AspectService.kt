package dsm.service.announcement.service.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import java.lang.Exception


@Component
@Aspect
public class AspectService {
    // TODO 에러핸들링 작성 & Metadata 처리 작성
    @Pointcut("execution(* dsm.service.announcement.service.AnnouncementServiceImpl.*(..))")
    fun tracingPointCut() {}

    @Around("tracingPointCut()")
    fun ErrorHandling(pjp: ProceedingJoinPoint): Any {
        try {
            return pjp.proceed()
        } catch (e: Exception) {
            println(e)
        }
        return pjp.proceed()
    }
}