package com.ysw.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ysw.data.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideAlarmDatabase(
        @ApplicationContext context: Context
    ): AlarmDatabase = Room.databaseBuilder(
        context = context,
        klass = AlarmDatabase::class.java,
        name = AlarmDatabase.name
    ).build()

    @Provides
    fun provideAlarmDao(alarmDatabase: AlarmDatabase) = alarmDatabase.alarmDao()

}