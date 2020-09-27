package dsm.service.announcement.infrastructure.repositories

import dsm.service.announcement.domain.exceptions.AuthorityFailedException
import dsm.service.announcement.domain.repositories.AuthRepository


class AuthRepositoryImpl: AuthRepository {
    override fun validateAuth(uuid: String, option: String) {
        if (uuid == "") throw AuthorityFailedException()
    }
}