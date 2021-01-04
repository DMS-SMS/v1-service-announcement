package dsm.service.announcement.core.domain.entity

import java.time.LocalDateTime

data class Announcement (
        val uuid: String,
        val number: Long?,
        val writerUuid: String,
        val date: LocalDateTime,
        var title: String,
        var targetGrade: String?,
        var targetClass: String?,
        val type: String,
        val club: String?,
        var content: String,
        val readAccounts: List<String>
)