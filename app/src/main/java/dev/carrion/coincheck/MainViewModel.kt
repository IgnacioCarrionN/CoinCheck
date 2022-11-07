package dev.carrion.coincheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.carrion.coincheck.common.architecture.StoreViewModel
import dev.carrion.common.coin_list.domain.store.MainEnvironment
import dev.carrion.common.coin_list.domain.store.MainStore
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    environment: MainEnvironment,
) : ViewModel(), StoreViewModel<MainStore.State, MainEnvironment> {

    override val store: MainStore = MainStore(environment, viewModelScope)
    override val coroutineScope: CoroutineScope
        get() = viewModelScope

}


