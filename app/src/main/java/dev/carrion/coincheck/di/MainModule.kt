package dev.carrion.coincheck.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.carrion.common.coin_list.domain.CoinListDataSource
import dev.carrion.common.coin_list.domain.CoinListRepository
import dev.carrion.common.coin_list.domain.CoinListLocalFirstRepository
import dev.carrion.common.coin_list.domain.GetCoinListUseCase
import dev.carrion.common.coin_list.domain.UpdateCoinListUseCase
import dev.carrion.common.coin_list.domain.store.MainEnvironment
import dev.carrion.common.coin_list.domain.store.MainEnvironmentImpl
import dev.carrion.common.coin_list.infrastructure.persistence.CoinListLocalDataSource
import dev.carrion.common.coin_list.infrastructure.remote.CoinListRemoteDataSource
import dev.carrion.database.infrastructure.DriverFactorySQLDelight
import dev.carrion.remote.ServerBuilder
import dev.carrion.sqldelight.database.CoinCheck

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideLocalDataSource(
        @ApplicationContext context: Context
    ): CoinListDataSource.Local =
        CoinListLocalDataSource(CoinCheck(driver = DriverFactorySQLDelight(context).createDriver()))

    @Provides
    fun provideRemoteDataSource(): CoinListDataSource.Remote =
        CoinListRemoteDataSource(ServerBuilder.getClient())

    @Provides
    fun provideCoinListRepository(
        local: CoinListDataSource.Local,
        remote: CoinListDataSource.Remote
    ): CoinListRepository =
        CoinListLocalFirstRepository(local, remote)

    @Provides
    fun provideGetUseCase(
        repository: CoinListRepository
    ): GetCoinListUseCase = GetCoinListUseCase(repository)

    @Provides
    fun provideUpdateUseCase(
        repository: CoinListRepository
    ): UpdateCoinListUseCase = UpdateCoinListUseCase(repository)

    @Provides
    fun provideMainEnvironment(
        getCoinListUseCase: GetCoinListUseCase,
        updateCoinListUseCase: UpdateCoinListUseCase
    ): MainEnvironment = MainEnvironmentImpl(getCoinListUseCase, updateCoinListUseCase)

}