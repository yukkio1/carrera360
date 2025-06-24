package com.example.inlab.apiresiduo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inlab.R
import com.example.inlab.apiresiduo.model.ResiduoResponse
import com.example.inlab.databinding.ItemResiduoBinding

class ResiduoAdapter(private var residuo: ResiduoResponse) : RecyclerView.Adapter<ResiduoAdapter.ResiduoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResiduoViewHolder {
        val binding = ItemResiduoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResiduoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResiduoViewHolder, position: Int) {
        holder.bind(residuo.nombreLogro[position])
    }

    override fun getItemCount(): Int = residuo.nombreLogro.size

    inner class ResiduoViewHolder(private val binding: ItemResiduoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nombreLogro: String) {
            binding.textViewNombreLogro.text = nombreLogro
        }
    }

    // Método para actualizar los datos
    fun updateData(newResiduo: ResiduoResponse) {
        this.residuo = newResiduo
        notifyDataSetChanged()
    }
}