package dev.carrion.architecture.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<T>(
    private inline val dataRepo: suspend  () -> T,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): T = withContext(Dispatchers.Default) {
        dataRepo()
    }

}

abstract class IOUseCase<T>(
    private inline val dataRepo: suspend () -> T,
): UseCase<T>(dataRepo, Dispatchers.Default)

abstract class UnconfinedUseCase<T>(
    private inline val dataRepo: suspend () -> T,
): UseCase<T>(dataRepo, Dispatchers.Unconfined)

abstract class MainUseCase<T>(
    private inline val dataRepo: suspend () -> T,
): UseCase<T>(dataRepo, Dispatchers.Main)
