package dsm.service.announcement

import dsm.service.announcement.application.mapper.AnnouncementMapper
import dsm.service.announcement.application.services.announcement.AnnouncementService
import dsm.service.announcement.application.services.announcement.AnnouncementServiceImpl
import dsm.service.announcement.domain.usecases.CreateAnnouncementUseCaseImpl
import dsm.service.announcement.domain.usecases.GetAnnouncementUseCaseImpl
import dsm.service.announcement.infrastructure.repositories.AnnouncementRepositoryImpl
import dsm.service.announcement.presentation.services.announcement.AnnouncementServicer
import io.grpc.ServerBuilder

class AnnouncementApplication constructor(
    private val port: Int,
    private val announcementServicer: AnnouncementServicer
) {
    val server = ServerBuilder
        .forPort(port)
        .addService(announcementServicer)
        .build()

    fun start() {
        server.start()
        println("* Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("* shutting down gRPC server since JVM is shutting down")
                this@AnnouncementApplication.stop()
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

fun createServicer(): AnnouncementServicer {
    val announcementService = AnnouncementServiceImpl(
        announcementMapper = AnnouncementMapper(),
        getAnnouncementUseCase = GetAnnouncementUseCaseImpl(),
        createAnnouncementUseCase = CreateAnnouncementUseCaseImpl(
            announcementRepository = AnnouncementRepositoryImpl()
        )
    )

    val announcementServicer = AnnouncementServicer(announcementService)

    return announcementServicer
}

fun main() {
    val port = 10000
    val announcementServicer = createServicer()
    val server = AnnouncementApplication(port, announcementServicer)

    server.start()
    server.blockUntilShutdown()

}