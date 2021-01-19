package dsm.service.announcement.core.domain.exception

import dsm.service.announcement.core.domain.exception.BusinessException

class UnAuthorizedException (
        statusCode: Int = 403,
        errorCode: Int = -1000,
        message: String = "UnAuthorized"
) : BusinessException(statusCode, errorCode, message)