package dsm.service.announcement.data.db.jpa.model

import com.mongodb.BasicDBObject
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection="test_col_announcement_detail")
data class AnnouncementDetailModel(
    @Id
    var uuid: String,
    var content: BasicDBObject,
    var read_accounts: MutableList<String>
)