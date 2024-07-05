package com.ysw.data.di

import com.ysw.data.repository.AlarmRepositoryImpl
import com.ysw.domain.repository.AlarmRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository

}