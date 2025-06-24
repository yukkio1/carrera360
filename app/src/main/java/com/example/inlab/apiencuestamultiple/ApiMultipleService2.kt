package com.carrera360.app_carrera360.apiencuestamultiple2

import com.carrera360.app_carrera360.apiencuestamultiple.ApiMultipleRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiMultipleService2 {
    @POST("encuestamultiple2/registrar_respuestas")
    fun registrarRespuestas(@Body request: ApiMultipleRequest): Call<ResponseBody>
}
