package dsm.service.announcement.core.domain.entity

import java.time.LocalDateTime
import java.util.*
import kotlin.streams.asSequence

data class Announcement (
        var uuid: String?,
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
) {
    fun generateUuid(): Announcement {
        this.uuid = "announcement-${generateRandomKey()}"
        return this
    }

    private fun generateRandomKey(): String {
        val source = "1234567890"
        return Random().ints(12, 0, source.length)
                .asSequence()
                .map(source::get)
                .joinToString("")
    }
}