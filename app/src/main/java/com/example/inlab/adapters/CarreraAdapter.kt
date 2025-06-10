package com.example.inlab.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inlab.databinding.ItemCarreraBinding
import com.example.inlab.modelo.Carrera

class CarreraAdapter(private val listaCarreras: List<Carrera>, private val listener: (Carrera) -> Unit) :
    RecyclerView.Adapter<CarreraAdapter.CarreraViewHolder>() {

    class CarreraViewHolder(private val binding: ItemCarreraBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(carrera: Carrera, listener: (Carrera) -> Unit) {
            binding.tituloCarrera.text = carrera.nombre
            binding.descripcionCarrera.text = carrera.descripcion
            binding.iconoCarrera.setImageResource(carrera.icono)
            binding.cardCarrera.setOnClickListener { listener(carrera) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarreraViewHolder {
        val binding = ItemCarreraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarreraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarreraViewHolder, position: Int) {
        holder.bind(listaCarreras[position], listener)
    }

    override fun getItemCount() = listaCarreras.size
}
