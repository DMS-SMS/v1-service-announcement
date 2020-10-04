package dsm.service.announcement.domain.repositories

interface AuthRepository {
    fun validateAuth(uuid: String, option: String)
}