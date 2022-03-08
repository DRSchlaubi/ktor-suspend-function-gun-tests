package dev.schlaubi.ktor_tests

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

val client = HttpClient()

@OptIn(ExperimentalTime::class)
suspend fun request() {
    val mark = TimeSource.Monotonic.markNow()
    val stack = ContextException()
    println("Captured stack trace after: ${mark.elapsedNow()}")

    try {
        client.get("https://www.google.com/teapot/")
    } catch (e: Throwable) {
        throw stack
            .apply {
                cause = e
                sanitizeStackTrace()
            }
    }
}

expect class ContextException @PublishedApi internal constructor() : RuntimeException {
    override var cause: Throwable?

    fun sanitizeStackTrace()
}
