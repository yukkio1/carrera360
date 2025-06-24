package com.example.inlab.apimodulofaltante

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ModuloFaltanteApi {
    @GET("api/moduloFaltante/{idUsuario}")
    fun getModulosFaltantes(@Path("idUsuario") idUsuario: Int): Call<List<ModuloFaltanteDTO>>
}