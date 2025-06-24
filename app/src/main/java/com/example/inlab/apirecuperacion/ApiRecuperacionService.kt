package com.example.inlab.apirecuperacion

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRecuperacionService {
    @POST("recuperacion/verificar_usuario")
    fun recuperarCuenta(@Body request: RecuperacionRequest): Call<ResponseBody>
}
