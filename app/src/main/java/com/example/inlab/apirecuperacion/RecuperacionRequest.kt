package com.example.inlab.apirecuperacion

data class RecuperacionRequest(
    val correo: String,
    val nombre: String,
    val apellido: String,
    val edad: Int
)
