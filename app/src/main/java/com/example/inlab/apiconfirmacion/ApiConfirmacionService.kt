package com.example.inlab.apiconfirmacion

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiConfirmacionService {
    @GET("confirmacion/{id_usuario}")
    fun verificarEncuesta(@Path("id_usuario") idUsuario: Int): Call<ConfirmacionResponse>
}
