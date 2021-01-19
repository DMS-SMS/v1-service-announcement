package dsm.service.announcement.infra.config.spring

import dsm.service.announcement.infra.consul.ConsulService
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component

@Component
class ShutdownEventListener(
    val consulService: ConsulService
): ApplicationListener<ContextClosedEvent> {
    override fun onApplicationEvent(event: ContextClosedEvent) {
        consulService.deregisterConsul()
    }
}