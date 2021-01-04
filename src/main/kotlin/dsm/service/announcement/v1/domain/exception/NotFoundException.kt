package dsm.service.announcement.v1.domain.exception

class NotFoundException(
        statusCode: Int = 404,
        errorCode: Int = -1001,
        message: String = "Not Found"
) : BusinessException(statusCode, errorCode, message)