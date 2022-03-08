package dev.schlaubi.ktor_tests

actual class ContextException : RuntimeException() {
    actual override var cause: Throwable? = null

    actual fun sanitizeStackTrace() {
        // Remove artifacts of stack trace capturing
        // at dev.schlaubi.ktor_tests.RequestKt.request(Request.kt:9)
        stackTrace = stackTrace.copyOfRange(1, stackTrace.size)
    }
}
