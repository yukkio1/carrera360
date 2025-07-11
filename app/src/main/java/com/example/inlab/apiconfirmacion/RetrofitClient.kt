package com.example.inlab.apiconfirmacion

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://test.sacooliveros.edu.pe:8081/" // ✅ Ajusta según la configuración del servidor

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // ✅ Convierte JSON en objetos Kotlin
            .build()
    }
}
