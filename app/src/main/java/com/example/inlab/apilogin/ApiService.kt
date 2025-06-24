package com.example.inlab.apilogin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("ingreso")
    fun loginUsuario(@Body request: LoginRequest): Call<LoginResponse>

    companion object {
        fun create(): ApiService {
            return RetrofitClient.instance.create(ApiService::class.java)
        }
    }
}
