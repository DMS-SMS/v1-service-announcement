package dsm.service.announcement.domain.usecases

interface ValidateUseCase {
    fun validateAnnouncementAuthority(announcementUuid: String, userUuid: String)

    fun validateAuth(uuid: String, option: String)
}