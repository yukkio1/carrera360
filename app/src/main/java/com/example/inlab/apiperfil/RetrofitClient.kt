package com.example.inlab.apiperfil

import com.example.inlab.apimodulofaltante.ModuloFaltanteApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://test.sacooliveros.edu.pe:8081/" // URL del servidor Spring Boot

    val perfilApi: PerfilApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PerfilApi::class.java)
    }
    val moduloFaltanteApi: ModuloFaltanteApi by lazy {
        Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ModuloFaltanteApi::class.java)
    }
}