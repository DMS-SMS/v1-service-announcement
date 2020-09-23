package dsm.service.announcement.domain.entities

import com.google.common.math.IntMath
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "announement")
open class Announcement(
    @Id
    @Column(name = "uuid")
    var uuid: Int? = null,

    @Column(name = "writer_uuid")
    var writerUuid: Int? = null,

    @Column(name = "date")
    var date: LocalDateTime? = null,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "content_id")
    var content: Int? = null,

    @Column(name = "target_grade")
    var targetGrade: Int? = null,

    @Column(name = "target_class")
    var targetClass: Int? = null,

    @Column(name = "type")
    var type: String? = null
)