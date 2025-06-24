package com.example.inlab.apiregistroModulo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/" // Usa 10.0.2.2 para conectar con localhost desde el emulador

    val registroModuloApi: RegistroModuloApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegistroModuloApi::class.java)
    }
}