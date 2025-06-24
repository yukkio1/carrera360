package com.example.inlab.apiregistroModulo

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface RegistroModuloApi {
    @POST("api/registroModulo")
    suspend fun registrarModulo(
        @Query("idUsuario") idUsuario: Int,
        @Query("idModulo") idModulo: Int
    ): Response<RegistroModuloResponse>
}