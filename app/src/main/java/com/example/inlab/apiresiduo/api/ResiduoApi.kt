package com.example.inlab.apiresiduo.api
import com.example.inlab.apiresiduo.model.ResiduoDTO
import com.example.inlab.apiresiduo.model.ResiduoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ResiduoApi {
    @GET("api/residuo/{id_usuario}")
    fun getResiduos(@Path("id_usuario") idUsuario: Int): Call<ResiduoResponse>
}