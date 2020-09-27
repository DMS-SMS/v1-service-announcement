package dsm.service.announcement.domain.exceptions

import java.lang.RuntimeException

open class BusinessException(
    val status: Int,
    val code: Int,
    message: String
): RuntimeException(message)