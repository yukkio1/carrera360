package com.example.inlab.apiinvitado

data class ApiInvitadoResponse(
    val idUsuario: Int,
    val usuario: String,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val correo: String,
    val contrasena: String,
    val fechaCreacion: String
)
