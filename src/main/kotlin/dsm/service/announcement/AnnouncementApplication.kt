package dsm.service.announcement

import dsm.service.announcement.application.mapper.AnnouncementMapper
import dsm.service.announcement.application.services.announcement.AnnouncementService
import dsm.service.announcement.application.services.announcement.AnnouncementServiceImpl
import dsm.service.announcement.domain.usecases.CreateAnnouncementUseCaseImpl
import dsm.service.announcement.domain.usecases.GetAnnouncementUseCaseImpl
import dsm.service.announcement.infrastructure.consul.ConsulManager
import dsm.service.announcement.infrastructure.repositories.AnnouncementRepositoryImpl
import dsm.service.announcement.presentation.servicers.AnnouncementServicer
import io.grpc.ServerBuilder

class AnnouncementApplication constructor(
    private val consulManager: ConsulManager,
    private val announcementServicer: AnnouncementServicer,
    private val port: Int = 10000
) {
    val server = ServerBuilder
        .forPort(port)
        .addService(announcementServicer)
        .build()

    fun start() {
        consulManager.registerConsul()
        server.start()
        println("* Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("* shutting down gRPC server since JVM is shutting down")
                this@AnnouncementApplication.stop()
                consulManager.deregisterConsul()
                println("* server shut down")
            }
        )
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}


fun main() {
    val server = AnnouncementApplication(
        ConsulManager()
        ,AnnouncementServicer(), 10123)

    server.start()
    server.blockUntilShutdown()

}