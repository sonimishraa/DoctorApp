package com.iotric.doctorplus.di

import android.content.Context
import com.iotric.doctorplus.networks.ServiceBuilder
import com.iotric.doctorplus.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext app: Context) = UserDatabase

    @Singleton
    @Provides
    fun getDao(db: UserDatabase) = db.userDao()

    @Singleton
    @Provides

    fun provideRetrofitInstance() = ServiceBuilder

}