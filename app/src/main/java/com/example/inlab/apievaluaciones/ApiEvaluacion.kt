package com.example.inlab.apievaluaciones

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiEvaluacion {
    @POST("api/evaluaciones")
    fun registrarEvaluacion(@Body request: EvaluacionRequest): Call<MensajeResponse>
}
