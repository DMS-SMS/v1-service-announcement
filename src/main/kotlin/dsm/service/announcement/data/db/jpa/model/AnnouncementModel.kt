package dsm.service.announcement.data.db.jpa.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_announcement")
data class AnnouncementModel(
    @Column(name = "uuid", length = 25)
    var uuid: String,

    @Id
    @Column(name = "number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var number: Long? = null,

    @Column(name = "writer_uuid", length = 20)
    var writerUuid: String,

    @Column(name = "date")
    var date: LocalDateTime,

    @Column(name = "title", length = 50)
    var title: String,

    @Column(name = "target_grade", length = 3)
    var targetGrade: String?,

    @Column(name = "target_group", length = 4)
    var targetGroup: String?,

    @Column(name = "type", length = 6)
    var type: String,

    @Column(name = "club")
    var club: String? = null
)