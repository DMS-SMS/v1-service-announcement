package dsm.service.announcement.v1.domain.exception

class UnAuthorizedException (
        statusCode: Int = 403,
        errorCode: Int = -1001,
        message: String = "UnAuthorized"
) : BusinessException(statusCode, errorCode, message)