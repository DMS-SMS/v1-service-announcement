package dsm.service.announcement.infra.config.spring

import dsm.service.announcement.infra.consul.ConsulService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class StartConfigure(
    val consulService: ConsulService
): CommandLineRunner {
    override fun run(vararg args: String?) {
        try {
            consulService.registerConsul()
        } catch (e: Exception) {
            println("** Register Consul Failed ** \n Detail : ($e) ")
            System.exit(42)
        }
    }
}