package dsm.service.announcement.infrastructure.consul

import com.orbitz.consul.AgentClient
import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import java.util.*

class ConsulManager(
    val serviceId: String = "DMS.SMS.v1.service.announcement",
    val client: Consul = Consul.builder().withUrl("http://127.0.0.1:8500").build(),
    val agentClient: AgentClient = client.agentClient()
) {
    fun registerConsul() {
        val service = ImmutableRegistration.builder()
            .id(serviceId)
            .name("DMS.SMS.v1.service.announcement")
            .port(10123)
            .check(Registration.RegCheck.ttl(10000000L))
            .tags(Collections.singletonList("announcement"))
            .meta(Collections.singletonMap("version", "0.0.1"))
            .build()

        agentClient.register(service)
        agentClient.pass(serviceId)
    }

    fun deregisterConsul() {
        agentClient.deregister(serviceId)
    }
}