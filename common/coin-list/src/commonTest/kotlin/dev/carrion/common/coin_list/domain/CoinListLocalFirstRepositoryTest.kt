package dev.carrion.common.coin_list.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class CoinListLocalFirstRepositoryTest {

    private lateinit var localDataSource: CoinListDataSource.Local

    private lateinit var remoteDataSource: CoinListDataSource.Remote

    private lateinit var repository: CoinListRepository

    var getRemoteCounter = 0
    var updateLocalCounter = 0
    var getLocalCounter = 0
    var isRemoteError = false

    @BeforeTest
    fun SetUp() {
        getRemoteCounter = 0
        isRemoteError = false
        localDataSource = object : CoinListDataSource.Local {

            override suspend fun updateCoinList(coinList: List<CoinListItem>) {
                updateLocalCounter++
            }

            override suspend fun getCoinList(): List<CoinListItem> {
                getLocalCounter++
                return emptyList()
            }
        }
        remoteDataSource = object : CoinListDataSource.Remote {
            override suspend fun getCoinList(): List<CoinListItem> {
                getRemoteCounter++
                if (isRemoteError) {
                    throw RemoteException("Test Error")
                }
                return emptyList()
            }
        }
        repository = CoinListLocalFirstRepository(localDataSource, remoteDataSource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should call local data source with getCoinList`() = runTest {
        repository.getCoinList()
        this.advanceUntilIdle()
        assertEquals(1, getLocalCounter)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should call remote data source with updateCoinList`() = runTest {
        repository.updateCoinList()
        this.advanceUntilIdle()
        assertEquals(1, getRemoteCounter)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should call update local data source after success updateCoinList`() = runTest {
        repository.updateCoinList()
        this.advanceUntilIdle()
        assertEquals(1, updateLocalCounter)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should not call update local data source after failure updateCoinList`() = runTest {
        isRemoteError = true
        repository.updateCoinList()
        this.advanceUntilIdle()
        assertEquals(0, updateLocalCounter)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return success on remote`() = runTest {
        val result = repository.updateCoinList()
        this.advanceUntilIdle()
        assertTrue(result.isSuccess)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return failure on remote`() = runTest {
        isRemoteError = true
        val result = repository.updateCoinList()
        this.advanceUntilIdle()
        assertTrue { result.isFailure }
    }

}