package com.example.inlab.apiperfil

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PerfilApi {
    @GET("perfil/{idUsuario}")
    fun getPerfil(@Path("idUsuario") idUsuario: Int): Call<PerfilDTO>
}