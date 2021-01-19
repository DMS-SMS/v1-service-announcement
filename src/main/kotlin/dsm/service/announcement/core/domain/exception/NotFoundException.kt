package dsm.service.announcement.core.domain.exception

import dsm.service.announcement.core.domain.exception.BusinessException

class NotFoundException(
        statusCode: Int = 404,
        errorCode: Int = -1000,
        message: String = "Not Found"
) : BusinessException(statusCode, errorCode, message)