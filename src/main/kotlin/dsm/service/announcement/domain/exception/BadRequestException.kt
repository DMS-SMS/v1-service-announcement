package dsm.service.announcement.domain.exception

import java.lang.RuntimeException

class BadRequestException(
        statusCode: Int = 400,
        errorCode: Int = -1002,
        message: String = "Bad Request"
) : BusinessException(statusCode, errorCode, message)