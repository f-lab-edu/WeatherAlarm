package com.ysw.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val alarmAppDispatcher: AlarmAppDispatchers)

enum class AlarmAppDispatchers {
    IO
}


@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {
    @Provides
    @Dispatcher(AlarmAppDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}