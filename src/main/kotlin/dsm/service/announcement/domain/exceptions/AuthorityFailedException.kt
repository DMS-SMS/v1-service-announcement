package dsm.service.announcement.domain.exceptions

class AuthorityFailedException: BusinessException {
    constructor(status: Int = 401,
                code: Int = -1001,
                message: String = "Authority Error"):
            super(status, code, message)
}