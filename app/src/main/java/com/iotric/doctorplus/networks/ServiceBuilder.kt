package com.iotric.doctorplus.networks

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.iotric.doctorplus.utils.Constants.BASE_URL
import com.rakuten.common.core.intercept.MockInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    fun getApiService(retrofit: Retrofit):ApiService {
        val apiService: ApiService = retrofit.create(ApiService::class.java)
        return apiService
    }

    // Add ".addInterceptor(MockInterceptor(application))" for mock interceptor
    // ".addInterceptor( ChuckerInterceptor(application)" for Response on your real device


    fun getRetrofit(application: Application): ApiService {
        val sharePref = application.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val authToken = sharePref.getString("authToken","")
        val client =
            OkHttpClient.Builder().addInterceptor(ChuckerInterceptor(application)).addInterceptor { chain -> chain.proceed(chain.request().newBuilder().also {reqBuilder->
                reqBuilder.addHeader("Authorization", "Bearer:${authToken}").header("Content-Type","application/json")
            }.build()) }.build()
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
