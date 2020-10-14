package dsm.service.announcement.infra.mongodb

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
class MongoDBConfiguration(
): AbstractMongoClientConfiguration() {
    override fun mappingMongoConverter(databaseFactory: MongoDatabaseFactory, customConversions: MongoCustomConversions, mappingContext: MongoMappingContext): MappingMongoConverter {
        val dbRefResolver = DefaultDbRefResolver(databaseFactory)
        val mappingMongoConverter = MappingMongoConverter(dbRefResolver, mappingContext)
        mappingMongoConverter.setCustomConversions(customConversions)
        mappingMongoConverter.setTypeMapper(DefaultMongoTypeMapper(null))
        return mappingMongoConverter
    }

    override fun mongoClient(): MongoClient {
        return MongoClients.create("mongodb://localhost:27017/javatest")
    }

    override fun getDatabaseName(): String {
        return "javatest"
    }
}