package com.example.inlab.apievaluaciones

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/" // Reemplaza con la IP de tu backend

    val apiService: ApiEvaluacion by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiEvaluacion::class.java)
    }
}
