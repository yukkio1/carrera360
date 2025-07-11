package com.example.inlab.apiresiduo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient3 {
    private const val BASE_URL = "http://test.sacooliveros.edu.pe:8081/"

    val residuoApi: ResiduoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ResiduoApi::class.java)
    }
}