package dsm.service.announcement.core.domain.entity

import java.time.LocalDateTime

data class Announcement (
        val uuid: String,
        val number: Long,
        val writerUuid: String,
        val date: LocalDateTime,
        val title: String,
        val targetGrade: String,
        val targetClass: String,
        val type: String,
        val club: String?,
        val content: String,
        val readAccounts: List<String>
)