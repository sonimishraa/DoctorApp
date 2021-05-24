package com.iotric.doctorplus.networks

import com.iotric.doctorplus.model.PuppyResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("END_POINT")
    suspend fun getResponse():Response<PuppyResponse>
}