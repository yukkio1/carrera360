package com.carrera360.app_carrera360.apiencuestamultiple

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiMultipleService {
    @POST("encuestamultiple/registrar_respuestas")
    fun registrarRespuestas(@Body request: ApiMultipleRequest): Call<ResponseBody>
}
