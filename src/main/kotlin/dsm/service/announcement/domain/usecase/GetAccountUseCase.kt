package dsm.service.announcement.domain.usecase

import dsm.service.announcement.domain.entity.Account

interface GetAccountUseCase {
    fun execute(uuid: String): Account?
}