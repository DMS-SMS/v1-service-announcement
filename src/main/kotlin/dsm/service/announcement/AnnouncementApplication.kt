package dsm.service.announcement

import dsm.service.announcement.application.service.announcement.AnnouncementServiceImpl
import dsm.service.announcement.presentation.service.announcement.AnnouncementServicer
import dsm.service.announcement.proto.AnnouncementServiceGrpcKt
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

fun main() {
    val port = 10000
    val announcementService = AnnouncementServiceImpl()
    val announcementServicer = AnnouncementServicer(announcementService)
    val server = AnnouncementApplication(port, announcementServicer)
    server.start()
    server.blockUntilShutdown()
}