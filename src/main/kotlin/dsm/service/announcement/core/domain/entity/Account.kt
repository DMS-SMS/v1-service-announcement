package dsm.service.announcement.core.domain.entity

import dsm.service.announcement.core.domain.entity.enums.AccountType

data class Account(
        val grade: Int,
        val group: Int,
        val name: String,
        val phoneNumber: String,
        val type: AccountType
)