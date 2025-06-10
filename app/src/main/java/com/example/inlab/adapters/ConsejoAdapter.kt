package com.example.inlab.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inlab.databinding.ItemConsejoBinding
import com.example.inlab.modelo.Consejo

class ConsejoAdapter(private val listaConsejos: List<Consejo>, private val listener: (Consejo) -> Unit) :
    RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder>() {

    class ConsejoViewHolder(private val binding: ItemConsejoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(consejo: Consejo, listener: (Consejo) -> Unit) {
            binding.tituloConsejo.text = consejo.nombre
            binding.descripcionConsejo.text = consejo.descripcion
            binding.iconoConsejo.setImageResource(consejo.icono)
            binding.cardConsejo.setOnClickListener { listener(consejo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsejoViewHolder {
        val binding = ItemConsejoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsejoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConsejoViewHolder, position: Int) {
        holder.bind(listaConsejos[position], listener)
    }

    override fun getItemCount() = listaConsejos.size
}
