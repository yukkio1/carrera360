package com.example.inlab.adapters
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inlab.R
import com.example.inlab.apimodulofaltante.ModuloFaltanteDTO

class ModuloFaltanteAdapter(private val modulos: List<ModuloFaltanteDTO>) :
    RecyclerView.Adapter<ModuloFaltanteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_modulo_faltante, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(modulos[position])
    }

    override fun getItemCount(): Int = modulos.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moduleNameTextView = itemView.findViewById<TextView>(R.id.moduleNameTextView)

        fun bind(modulo: ModuloFaltanteDTO) {
            moduleNameTextView.text = "${modulo.idModulo}.- ${modulo.nombreModulo}"
        }
    }
}