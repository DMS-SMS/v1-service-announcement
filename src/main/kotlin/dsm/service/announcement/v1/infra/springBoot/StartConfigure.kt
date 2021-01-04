package dsm.service.announcement.v1.infra.springBoot

import dsm.service.announcement.v1.infra.consul.ConsulHandler
import org.lognet.springboot.grpc.autoconfigure.GRpcServerProperties
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class StartConfigure(
        val consulHandler: ConsulHandler
): CommandLineRunner {
    override fun run(vararg args: String?) {
        try {
            consulHandler.registerConsul()
        } catch (e: Exception) {
            println("** Register Consul Failed ** \n Detail : ($e) ")
            System.exit(42)
        }
    }
}