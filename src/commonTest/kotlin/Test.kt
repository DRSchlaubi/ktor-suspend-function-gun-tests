import dev.schlaubi.ktor_tests.request
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertTrue

class Test {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test() = runTest {
        try {
            request()
        } catch (e: Exception) {
            e.printStackTrace()
            val trace = e.stackTraceToString()

            assertTrue("Test.kt:11" in trace || "kfun:Test#test()" in trace)
        }
    }
}
