package com.carrera360.app_carrera360.apiencuesta

data class ApiEncuestaRequest(
    val idUsuario: Int, // ✅ ID del usuario
    val textoAlternativa: String // ✅ Alternativa seleccionada
)
