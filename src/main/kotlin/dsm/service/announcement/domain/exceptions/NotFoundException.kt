package dsm.service.announcement.domain.exceptions

class NotFoundException: BusinessException {
    constructor(status: Int = 404,
                code: Int = -1001,
                message: String = "Not Found"):
            super(status, code, message)
} {
}