package com.iotric.doctorplus.networks

import com.iotric.doctorplus.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val client= OkHttpClient.Builder().build()
    var retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
     var apiService: ApiService = retrofit.create(ApiService::class.java)
}