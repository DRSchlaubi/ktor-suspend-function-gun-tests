package dev.schlaubi.ktor_tests

actual class ContextException : RuntimeException() {
    actual override var cause: Throwable? = null

    actual fun sanitizeStackTrace() {
        // Native adds the following artifacts, which are pretty much non removeable
        //    at kfun:kotlin.Throwable#<init>(){} (0x438186)
        //    at kfun:kotlin.Exception#<init>(){} (0x431a1c)
        //    at kfun:kotlin.RuntimeException#<init>(){} (0x431bbc)
        //    at kfun:dev.schlaubi.ktor_tests.ContextException#<init>(){} (0x41f2ec)
    }
}
