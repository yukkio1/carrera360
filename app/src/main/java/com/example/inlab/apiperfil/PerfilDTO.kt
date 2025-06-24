package com.example.inlab.apiperfil

data class PerfilDTO(
    val respuestasIniciales: List<String>,
    val cantidadEvaluacionesCompletadas: Int,
    val cantidadModulos: Int,
    val cantidadEvaluaciones: Int,
    val cantidadModulosLeidos: Int,
    val cantidadLogros: Int
)
