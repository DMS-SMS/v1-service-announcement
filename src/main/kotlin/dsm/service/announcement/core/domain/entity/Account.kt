package dsm.service.announcement.core.domain.entity

import dsm.service.announcement.core.domain.entity.enums.AccountType

data class Account(
        var grade: Int,
        var group: Int,
        var name: String,
        var phoneNumber: String,
        var type: AccountType
)