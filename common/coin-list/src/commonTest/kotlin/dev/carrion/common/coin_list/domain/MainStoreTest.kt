package dev.carrion.common.coin_list.domain

import dev.carrion.architecture.resource.Resource
import dev.carrion.common.coin_list.domain.store.MainEnvironment
import dev.carrion.common.coin_list.domain.store.MainStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.*

class MainStoreTest {

    lateinit var environment: MainEnvironmentFake
    lateinit var coinList: List<CoinListItem>

    @OptIn(ExperimentalCoroutinesApi::class)
    lateinit var scope: TestScope
    lateinit var store: MainStore

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun SetUp() {
        coinList = List(3) { CoinListItemMother.create() }
        environment = MainEnvironmentFake(
            coinList = coinList
        )
        scope = TestScope()
        store = MainStore(
            environment,
            scope
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On init State has the coin list and Remote loaded`() {
        val expected = MainStore.State(
            coinList = Resource.Loaded(coinList),
            remote = Resource.Loaded(Unit)
        )
        scope.advanceUntilIdle()
        assertEquals(expected, store.stateFlow.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On remote error State has coin list and Remote returns Failure`() {
        environment.updateSuccess = false
        scope.advanceUntilIdle()
        val actual = store.stateFlow.value
        assertTrue(actual.remote is Resource.Error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On Init GetCoinList is called twice`() {
        scope.advanceUntilIdle()
        assertEquals(2, environment.getCoinListCount)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On Init UpdateCoinList is called`() {
        scope.advanceUntilIdle()
        assertEquals(1, environment.updateCoinListCount)
    }

}

class MainEnvironmentFake(
    var coinList: List<CoinListItem>,
    var updateSuccess: Boolean = true
) : MainEnvironment {

    var getCoinListCount = 0
    var updateCoinListCount = 0

    override suspend fun getCoinList(): List<CoinListItem> {
        getCoinListCount++
        return coinList
    }

    override suspend fun updateCoinList(): Result<Unit> {
        updateCoinListCount++
        return if (updateSuccess) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Test error"))
        }
    }
}