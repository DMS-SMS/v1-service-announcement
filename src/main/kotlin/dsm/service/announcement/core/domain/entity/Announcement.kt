package dsm.service.announcement.core.domain.entity

import java.time.LocalDateTime
import java.util.*
import kotlin.streams.asSequence

data class Announcement (
        var uuid: String,
        var number: Long?,
        var writerUuid: String,
        var date: LocalDateTime,
        var title: String,
        var targetGrade: String?,
        var targetClass: String?,
        var type: String,
        var club: String?,
        var content: String,
        var readAccounts: List<String>
)