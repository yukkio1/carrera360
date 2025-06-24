package com.example.inlab.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsuarioViewModel : ViewModel() {

    private val _idUsuario = MutableLiveData<Int>()
    val idUsuario: LiveData<Int> get() = _idUsuario

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> get() = _nombre

    private val _apellido = MutableLiveData<String>()
    val apellido: LiveData<String> get() = _apellido

    private val _edad = MutableLiveData<Int>()
    val edad: LiveData<Int> get() = _edad

    private val _correo = MutableLiveData<String>()
    val correo: LiveData<String> get() = _correo

    private val _usuario = MutableLiveData<String>()
    val usuario: LiveData<String> get() = _usuario

    private val _fechaCreacion = MutableLiveData<String>()
    val fechaCreacion: LiveData<String> get() = _fechaCreacion

    fun setUsuario(
        id: Int,
        nom: String,
        ape: String,
        ed: Int,
        mail: String,
        user: String,
        fecha: String
    ) {
        _idUsuario.value = id
        _nombre.value = nom
        _apellido.value = ape
        _edad.value = ed
        _correo.value = mail
        _usuario.value = user
        _fechaCreacion.value = fecha
    }
}
