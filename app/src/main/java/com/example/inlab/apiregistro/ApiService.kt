package com.example.inlab.apiregistro

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("usuarios")
    fun registrarUsuario(@Body usuario: Usuario): Call<UsuarioResponse> // ✅ Ahora devuelve un objeto JSON
}
