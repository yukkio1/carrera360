package com.example.inlab.apiresiduo.service
import com.example.inlab.apiresiduo.api.RetrofitClient3
import com.example.inlab.apiresiduo.api.ResiduoApi
import com.example.inlab.apiresiduo.model.ResiduoDTO
import com.example.inlab.apiresiduo.model.ResiduoResponse
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class ResiduoService {

    suspend fun getResiduosByUserId(idUsuario: Int): ResiduoResponse? {
        return try {
            RetrofitClient3.residuoApi.getResiduos(idUsuario).execute().body()
        } catch (e: Exception) {
            null
        }
    }
}