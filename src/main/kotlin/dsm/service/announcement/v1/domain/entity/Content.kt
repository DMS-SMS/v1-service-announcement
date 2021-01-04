package dsm.service.announcement.v1.domain.entity

import com.mongodb.BasicDBObject
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection="col_content")
data class Content(
        @Id
        var uuid: String,
        var content: BasicDBObject
)