package dsm.service.announcement.core.domain.exception

import dsm.service.announcement.core.domain.exception.BusinessException

class ServerException (
        statusCode: Int = 500,
        errorCode: Int = -1000,
        message: String = "Server Error"
) : BusinessException(statusCode, errorCode, message)