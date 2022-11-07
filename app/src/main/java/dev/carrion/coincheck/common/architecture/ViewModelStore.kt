package dev.carrion.coincheck.common.architecture


import dev.carrion.architecture.store.Action
import dev.carrion.architecture.store.Environment
import dev.carrion.architecture.store.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface StoreViewModel<State, E : Environment> {

    val store: Store<State, E>
    val coroutineScope: CoroutineScope

    val state: StateFlow<State>
        get() = store.stateFlow

    fun send(action: Action<State, E>) {
        coroutineScope.launch {
            store.sendAction(action)
        }
    }

}