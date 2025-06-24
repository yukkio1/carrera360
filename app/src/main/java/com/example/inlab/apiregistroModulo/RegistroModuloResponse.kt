package com.example.inlab.apiregistroModulo

data class RegistroModuloResponse(
    val mensaje: String,
    val moduloLeido: ModuloLeidoRegistro? = null
)