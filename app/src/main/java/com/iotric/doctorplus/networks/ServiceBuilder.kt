package com.iotric.doctorplus.networks

import android.app.Application
import com.iotric.doctorplus.utils.Constants.BASE_URL
import com.rakuten.common.core.intercept.MockInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    fun getApiService(retrofit: Retrofit):ApiService {
        val apiService: ApiService = retrofit.create(ApiService::class.java)
        return apiService
    }

    fun getRetrofit(application: Application): ApiService {
        val client = OkHttpClient.Builder().addInterceptor(MockInterceptor(application)).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)
        return apiService
    }

    fun getRetrofitInstance(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }

    fun getOkHttpClient( application: Application): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(MockInterceptor(application)).build()
        return client
    }
}