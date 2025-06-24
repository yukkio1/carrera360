package com.carrera360.app_carrera360.apiencuestamultiple

data class ApiMultipleRequest(
    val idUsuario: Int,
    val textoAlternativas: List<String>
)
