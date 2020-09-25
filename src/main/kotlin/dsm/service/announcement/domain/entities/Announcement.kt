package dsm.service.announcement.domain.entities

import com.google.common.math.IntMath
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "announcement")
open class Announcement(
    @Column(name = "uuid")
    open var uuid: String? = null,

    @Id
    @Column(name = "number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var number: Long? = null,

    @Column(name = "writer_uuid")
    open var writerUuid: String? = null,

    @Column(name = "date")
    open var date: LocalDateTime? = null,

    @Column(name = "title")
    open var title: String? = null,

    @Column(name = "content_id")
    open var contentUuid: String? = null,

    @Column(name = "target_grade")
    open var targetGrade: Int? = null,

    @Column(name = "target_class")
    open var targetClass: Int? = null,

    @Column(name = "type")
    open var type: String? = null
)