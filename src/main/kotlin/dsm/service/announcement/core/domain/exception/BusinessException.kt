package dsm.service.announcement.core.domain.exception

import java.lang.RuntimeException

open class BusinessException(
        val statusCode: Int,
        val errorCode: Int,
        override val message: String
): RuntimeException(message)