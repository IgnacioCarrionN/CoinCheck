package dev.carrion.architecture.store

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

abstract class BaseStore<S, E : Environment>(
    private val environment: E,
    private val initialState: S,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : Store<S, E> {

    private val mutableState: MutableStateFlow<S> = MutableStateFlow(initialState)
    override val stateFlow: StateFlow<S>
        get() = mutableState.asStateFlow()

    // TODO Change context to Main when I can test Dispatcher.Main
    override suspend fun sendAction(action: Action<S, E>) = send(action)


    private suspend fun send(action: Action<S, E>) {
        val effect = action.execute(
            environment,
            { stateFlow.value }
        ) {
            mutableState.value = it
        }
        if (effect != null) {
            sendAction(effect)
        }
    }

}

interface Store<S, E : Environment> {
    val stateFlow: StateFlow<S>

    suspend fun sendAction(action: Action<S, E>)
}

interface Action<State, E : Environment> {
    suspend fun execute(
        environment: E,
        currentState: () -> State,
        reducer: (State) -> Unit
    ): Action<State, E>?
}

interface Environment