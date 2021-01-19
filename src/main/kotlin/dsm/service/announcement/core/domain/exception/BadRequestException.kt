package dsm.service.announcement.core.domain.exception

class BadRequestException(
        statusCode: Int = 400,
        errorCode: Int = -1000,
        message: String = "Bad Request"
) : BusinessException(statusCode, errorCode, message)