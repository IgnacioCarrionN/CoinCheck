package dev.carrion.common.coin_list.domain.store

import dev.carrion.architecture.resource.Resource
import dev.carrion.architecture.store.Action
import dev.carrion.architecture.store.BaseStore
import dev.carrion.architecture.store.Environment
import dev.carrion.common.coin_list.domain.CoinListItem
import dev.carrion.common.coin_list.domain.GetCoinListUseCase
import dev.carrion.common.coin_list.domain.UpdateCoinListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainStore(
    environment: MainEnvironment,
    scope: CoroutineScope
) : BaseStore<MainStore.State, MainEnvironment>(environment, State()) {

    init {
        scope.launch {
            sendAction(MainAction.GetCoinList)
            sendAction(MainAction.UpdateCoinList)
        }
    }


    data class State(
        val coinList: Resource<List<CoinListItem>> = Resource.Idle,
        val remote: Resource<Unit> = Resource.Idle
    )


}

interface MainEnvironment : Environment {
    suspend fun getCoinList(): List<CoinListItem>
    suspend fun updateCoinList(): Result<Unit>
}

class MainEnvironmentImpl(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val updateCoinListUseCase: UpdateCoinListUseCase
) : MainEnvironment {
    override suspend fun getCoinList(): List<CoinListItem> {
        return getCoinListUseCase()
    }

    override suspend fun updateCoinList(): Result<Unit> {
        return updateCoinListUseCase()
    }
}

sealed interface MainAction : Action<MainStore.State, MainEnvironment> {

    object UpdateCoinList : MainAction {
        override suspend fun execute(
            environment: MainEnvironment,
            currentState: () -> MainStore.State,
            reducer: (MainStore.State) -> Unit
        ): MainAction {
            reducer(currentState().copy(remote = Resource.Loading))
            environment.updateCoinList().fold(
                onSuccess = {
                    return UpdateCoinListSuccess
                },
                onFailure = {
                    return UpdateCoinListError(it)
                }
            )
        }
    }

    object GetCoinList : MainAction {
        override suspend fun execute(
            environment: MainEnvironment,
            currentState: () -> MainStore.State,
            reducer: (MainStore.State) -> Unit
        ): MainAction {
            val newCoinList = environment.getCoinList()
            return GetCoinListSuccess(newCoinList)
        }
    }
}

internal data class GetCoinListSuccess(val data: List<CoinListItem>) : MainAction {
    override suspend fun execute(
        environment: MainEnvironment,
        currentState: () -> MainStore.State,
        reducer: (MainStore.State) -> Unit
    ): Action<MainStore.State, MainEnvironment>? {
        reducer(currentState().copy(coinList = Resource.Loaded(data)))
        return null
    }
}

internal object UpdateCoinListSuccess : MainAction {
    override suspend fun execute(
        environment: MainEnvironment,
        currentState: () -> MainStore.State,
        reducer: (MainStore.State) -> Unit
    ): Action<MainStore.State, MainEnvironment> {
        reducer(currentState().copy(remote = Resource.Loaded(Unit)))
        return MainAction.GetCoinList
    }
}

internal data class UpdateCoinListError(val error: Throwable) : MainAction {
    override suspend fun execute(
        environment: MainEnvironment,
        currentState: () -> MainStore.State,
        reducer: (MainStore.State) -> Unit
    ): Action<MainStore.State, MainEnvironment>? {
        reducer(currentState().copy(remote = Resource.Error(error)))
        return null
    }
}