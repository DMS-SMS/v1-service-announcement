package dsm.service.announcement

import dsm.service.announcement.proto.AnnouncementServiceGrpcKt
import io.grpc.ServerBuilder

class AnnouncementApplication constructor(
    private val port: Int
) {
    val server = ServerBuilder
        .forPort(port)
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@AnnouncementApplication.stop()
                println("*** server shut down")
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
    val server = AnnouncementApplication(port)
    server.start()
    server.blockUntilShutdown()
}