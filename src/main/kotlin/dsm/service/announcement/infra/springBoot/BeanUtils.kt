package dsm.service.announcement.infra.springBoot

import org.springframework.context.ApplicationContext


fun getBean(beanName: String): Any {
    val applicationContext: ApplicationContext = ApplicationContextProvider.context
    return applicationContext.getBean(beanName)
}
