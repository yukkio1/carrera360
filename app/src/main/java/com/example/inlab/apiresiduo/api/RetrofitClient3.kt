package com.example.inlab.apiresiduo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient3 {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val residuoApi: ResiduoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ResiduoApi::class.java)
    }
}