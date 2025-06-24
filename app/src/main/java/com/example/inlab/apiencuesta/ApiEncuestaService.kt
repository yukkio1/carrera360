package com.carrera360.app_carrera360.apiencuesta

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiEncuestaService {
    @Headers("Content-Type: application/json")
    @POST("/encuesta/registrar_respuesta")
    fun registrarRespuesta(@Body request: ApiEncuestaRequest): Call<ApiEncuestaResponse> // ✅ Envia JSON a la API
}
