import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
actual fun runTest(block: suspend () -> Unit) {
    runTest { block() }
}
