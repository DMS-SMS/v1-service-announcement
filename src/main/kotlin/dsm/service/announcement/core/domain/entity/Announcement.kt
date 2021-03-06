package dsm.service.announcement.core.domain.entity

import java.time.LocalDateTime

data class Announcement (
        var uuid: String,
        var number: Long?,
        var writerUuid: String,
        var writerName: String? = null,
        var date: LocalDateTime,
        var title: String,
        var targetGrade: String?,
        var targetClass: String?,
        var type: String,
        var club: String?,
        var content: String,
        var readAccounts: MutableList<String> = arrayListOf(),
        var isCheck: Boolean = false
) {
    private fun announcementCheck(): Announcement {
        this.isCheck = true
        return this
    }

    fun read(accountUuid: String): Announcement {
        announcementCheck()
        if (!readAccounts.contains(accountUuid)) readAccounts.add(accountUuid)
        return this
    }
}