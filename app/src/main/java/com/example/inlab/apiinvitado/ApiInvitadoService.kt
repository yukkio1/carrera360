package com.example.inlab.apiinvitado

import retrofit2.Call
import retrofit2.http.POST

interface ApiInvitadoService {
    @POST("/crear-invitado")
    fun crearInvitado(): Call<ApiInvitadoResponse>
}
