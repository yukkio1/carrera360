package com.example.inlab

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.inlab.databinding.ActivityMainBinding
import com.example.inlab.viewmodel.UsuarioViewModel
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // ✅ ViewModel compartido accesible para fragments
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Leer datos del intent (enviados desde Question5Activity)
        val idUsuario = intent.getIntExtra("idUsuario", -1)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val apellido = intent.getStringExtra("apellido") ?: ""
        val edad = intent.getIntExtra("edad", -1)
        val correo = intent.getStringExtra("correo") ?: ""
        val usuario = intent.getStringExtra("usuario") ?: ""
        val fechaCreacion = intent.getStringExtra("fechaCreacion") ?: ""

        // ✅ Pasar los datos al ViewModel
        usuarioViewModel.setUsuario(
            id = idUsuario,
            nom = nombre,
            ape = apellido,
            ed = edad,
            mail = correo,
            user = usuario,
            fecha = fechaCreacion
        )

        // ✅ Configurar navegación inferior con el host fragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
