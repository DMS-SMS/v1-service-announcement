package dsm.service.announcement.infra.consul

import com.orbitz.consul.AgentClient
import com.orbitz.consul.Consul
import com.orbitz.consul.KeyValueClient
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import com.orbitz.consul.model.health.Service
import dsm.service.announcement.domain.exception.NotFoundException
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort
import org.springframework.stereotype.Component
import java.util.*
import kotlin.random.Random


@Component
public class ConsulHandler(
        val serviceName: String = "DMS.SMS.v1.service.announcement",
        val client: Consul = Consul.builder().withUrl("http://${System.getenv("CONSUL_ADDR")}").build(),
        val agentClient: AgentClient = client.agentClient(),
        val kvClient: KeyValueClient = client.keyValueClient(),
        val serviceId: String = "$serviceName-${UUID.randomUUID()}",
        @LocalRunningGrpcPort val port: Int = 0
) {
    fun registerConsul(): String {
        val service = ImmutableRegistration.builder()
                .id(serviceId)
                .name(serviceName)
                .port(port)
                .check(Registration.RegCheck.ttl(100000000L))
                .tags(Collections.singletonList("Announcement"))
                .meta(Collections.singletonMap("version","0.0.1"))
                .build()

        agentClient.register(service)
        agentClient.pass(serviceId)
        return serviceId
    }

    fun deregisterConsul() {
        agentClient.deregister(serviceId)
    }

    fun getValue(key: String): JSONObject {
        val parser = JSONParser()
        val value: String = kvClient.getValueAsString(key).orElse("{}")
        return try {
            parser.parse(value) as JSONObject
        } catch (e: Exception) {
            JSONObject()
        }
    }

    fun getServiceHost(serviceName: String): String {
        return getService(serviceName)?.address ?: throw NotFoundException(
                statusCode = 500, message = "Consul Get Service Failed (Address)")
    }

    fun getServicePort(serviceName: String): Int {
        return getService(serviceName)?.port ?: throw NotFoundException(
                statusCode = 500, message = "Consul Get Service Failed (Port)"
        )
    }

    private fun getService(serviceName: String): Service? {
        for ((_, value) in agentClient.services) {
            if (value.service.equals(serviceName)) {
                if (agentClient.checks["service:" + value.id]!!.status == "passing") return value
            }
        }
        return null
    }
}