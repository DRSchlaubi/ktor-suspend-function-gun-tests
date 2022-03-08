# ktor-exception-recovery

This project tries to recover stack traces lost by Ktors `SuspendFunctionGun`

# Before (JVM)
```
2022-03-08T17:23:07.552555658Z dev.kord.rest.request.KtorRequestException: REST request returned an error: 403 Forbidden Missing Permissions null
2022-03-08T17:23:07.552558273Z at dev.kord.rest.request.KtorRequestHandler.handle(KtorRequestHandler.kt:62)
2022-03-08T17:23:07.552560878Z at dev.kord.rest.request.KtorRequestHandler$handle$1.invokeSuspend(KtorRequestHandler.kt)
2022-03-08T17:23:07.552563202Z at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
2022-03-08T17:23:07.552584202Z at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:191)
2022-03-08T17:23:07.552587498Z at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:147)
2022-03-08T17:23:07.552589823Z at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:15)
2022-03-08T17:23:07.552592077Z at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:93)
2022-03-08T17:23:07.552611394Z at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
2022-03-08T17:23:07.552613838Z at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:191)
2022-03-08T17:23:07.552616383Z at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:147)
2022-03-08T17:23:07.552618677Z at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:15)
2022-03-08T17:23:07.552620982Z at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:93)
2022-03-08T17:23:07.552623297Z at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
2022-03-08T17:23:07.552625581Z at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:191)
2022-03-08T17:23:07.552628377Z at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:147)
2022-03-08T17:23:07.552631182Z at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:15)
2022-03-08T17:23:07.552633987Z at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:93)
2022-03-08T17:23:07.552636842Z at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
2022-03-08T17:23:07.552639247Z at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:191)
2022-03-08T17:23:07.552642453Z at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:147)
2022-03-08T17:23:07.552644887Z at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:15)
2022-03-08T17:23:07.552648324Z at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:93)
2022-03-08T17:23:07.552651199Z at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
2022-03-08T17:23:07.552653995Z at io.ktor.util.pipeline.SuspendFunctionGun.resumeRootWith(SuspendFunctionGun.kt:191)
2022-03-08T17:23:07.552656720Z at io.ktor.util.pipeline.SuspendFunctionGun.loop(SuspendFunctionGun.kt:147)
2022-03-08T17:23:07.552658975Z at io.ktor.util.pipeline.SuspendFunctionGun.access$loop(SuspendFunctionGun.kt:15)
2022-03-08T17:23:07.552661259Z at io.ktor.util.pipeline.SuspendFunctionGun$continuation$1.resumeWith(SuspendFunctionGun.kt:93)
2022-03-08T17:23:07.552663533Z at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:46)
2022-03-08T17:23:07.552665788Z at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
2022-03-08T17:23:07.552668012Z at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:571)
2022-03-08T17:23:07.552670286Z at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:750)
2022-03-08T17:23:07.552672550Z at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:678)
2022-03-08T17:23:07.552676969Z at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:665)
```

# Solution
Similarly to JDAs [ContextException](https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/exceptions/ContextException.html)
this approach creates an exception each time you create a request and then throws that one instead, because it has the original stack trace
```
dev.schlaubi.ktor_tests.ContextException
	at Test$test$1.invokeSuspend(Test.kt:11)
	at Test$test$1.invoke(Test.kt)
	at Test$test$1.invoke(Test.kt)
	at TestToolsKt$runTest$1.invokeSuspend(TestTools.kt:6)
	at TestToolsKt$runTest$1.invoke(TestTools.kt)
	at TestToolsKt$runTest$1.invoke(TestTools.kt)
Caused by: [actual exception]
[...]
```

This works on all supported platforms, although native doesn't have enough control over exceptions to remove a few 
artifacts of the stack trace capturing resulting in this beeing at the top of the stack trace
```
    at kfun:kotlin.Throwable#<init>(){} (0x438426)
    at kfun:kotlin.Exception#<init>(){} (0x431cbc)
    at kfun:kotlin.RuntimeException#<init>(){} (0x431e5c)
    at kfun:dev.schlaubi.ktor_tests.ContextException#<init>(){} (0x41f50c)
```
