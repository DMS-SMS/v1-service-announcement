package dsm.service.announcement.infrastructure.util

import java.util.*
import kotlin.streams.asSequence


class RandomKey {
    fun generateRandomKey(length: Long):String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWZYZabcdefghijklmnopqrstuvwxtz123457890"
        return Random().ints(length, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
    }
}