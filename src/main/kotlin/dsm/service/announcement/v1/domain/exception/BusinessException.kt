package dsm.service.announcement.v1.domain.exception

import java.lang.RuntimeException

open class BusinessException(
        var statusCode: Int,
        var errorCode: Int,
        override var message: String
): RuntimeException(message)