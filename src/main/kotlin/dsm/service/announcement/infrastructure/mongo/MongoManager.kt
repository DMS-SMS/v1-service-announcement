package dsm.service.announcement.infrastructure.mongo

import com.mongodb.MongoClient
import com.mongodb.ServerAddress

object MongoManager {
    val mongoAddress: String = "127.0.0.1"
    val mongoPort: Int = 27017
    val mongoDBName: String = "javatest"

    val mongoClient = MongoClient(ServerAddress(mongoAddress, mongoPort))
    val db = mongoClient.getDatabase(mongoDBName)
    val collection = db.getCollection("contents")
}