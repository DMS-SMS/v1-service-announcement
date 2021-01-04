package dsm.service.announcement.v1.domain.usecase

import dsm.service.announcement.v1.domain.entity.Account

interface GetAccountUseCase {
    fun execute(uuid: String): Account?
}