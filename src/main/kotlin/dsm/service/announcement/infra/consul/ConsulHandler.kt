package dsm.service.announcement.infra.consul

import com.orbitz.consul.AgentClient
import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import org.springframework.stereotype.Component
import java.util.*


@Component
public class ConsulHandler(
        val serviceId: String = "DMS.SMS.v1.service.announcement",
        val client: Consul = Consul.builder().withUrl("http://127.0.0.1:8500").build(),
        val agentClient: AgentClient = client.agentClient()
) {
    fun registerConsul() {
        val service = ImmutableRegistration.builder()
                .id(serviceId)
                .name(serviceId)
                .port(10999)
                .check(Registration.RegCheck.ttl(100000000L))
                .tags(Collections.singletonList("Announcement"))
                .meta(Collections.singletonMap("version","0.0.1"))
                .build()

        agentClient.register(service)
        agentClient.pass(serviceId)
    }

    fun deregisterConsul() {
        agentClient.deregister(serviceId)
    }
}