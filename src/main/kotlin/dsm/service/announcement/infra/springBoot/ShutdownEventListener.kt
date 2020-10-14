package dsm.service.announcement.infra.springBoot

import dsm.service.announcement.infra.consul.ConsulHandler
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component


@Component
class ShutdownEventListener(
        val consulHandler: ConsulHandler
): ApplicationListener<ContextClosedEvent> {
    override fun onApplicationEvent(event: ContextClosedEvent) {
        consulHandler.deregisterConsul()
    }
}