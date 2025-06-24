package com.example.inlab.apilogin

import com.google.gson.annotations.SerializedName
import java.util.Date

data class LoginResponse(
    val idUsuario: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val correo: String,
    val usuario: String,
    @SerializedName("fechaCreacion") val fechaCreacion: String, // ✅ Cambiado
    val mensaje: String
)

