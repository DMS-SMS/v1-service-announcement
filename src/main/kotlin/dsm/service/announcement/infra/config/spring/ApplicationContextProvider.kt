package dsm.service.announcement.infra.config.spring

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class ApplicationContextProvider: ApplicationContextAware {
    companion object {
        lateinit var context: ApplicationContext
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        ApplicationContextProvider.context = applicationContext
    }
}