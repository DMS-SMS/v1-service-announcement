package dsm.service.announcement.v1.domain.exception

class ServerException (
        statusCode: Int = 500,
        errorCode: Int = -1000,
        message: String = "Server Error"
) : BusinessException(statusCode, errorCode, message)