package com.iotric.doctorplus.di

import android.app.Application
import android.content.Context
import com.iotric.doctorplus.networks.ApiService
import com.iotric.doctorplus.networks.ServiceBuilder
import com.iotric.doctorplus.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext app: Context) = UserDatabase

    @Singleton
    @Provides
    fun getDao(db: UserDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideRetrofit(application: Application): ApiService  =
        ServiceBuilder.getRetrofit(application)

    @Singleton
    @Provides
    fun provideOkHttpClient(application: Application): OkHttpClient  =
        ServiceBuilder.getOkHttpClient(application)

    @Singleton
    @Provides
    fun provideRetrofitInstance(client: OkHttpClient): Retrofit  =
        ServiceBuilder.getRetrofitInstance(client)


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        ServiceBuilder.getApiService(retrofit)

}