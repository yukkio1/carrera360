package com.example.inlab
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class PrioritiesAdapter(
    private val prioritiesList: MutableList<String>,
    private val itemTouchHelper: ItemTouchHelper // Asegurar que el parámetro se recibe
) : RecyclerView.Adapter<PrioritiesAdapter.ViewHolder>() {


    private var itemMoveListener: (() -> Unit)? = null
    private var selectedPosition: Int? = null

    fun setOnItemMoveListener(listener: () -> Unit) {
        itemMoveListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val priorityText: TextView = itemView.findViewById(R.id.priorityText)
        val dragHandle: ImageView = itemView.findViewById(R.id.dragHandle)

        init {
            itemView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP && event.eventTime - event.downTime < 300) {
                    toggleSelection(bindingAdapterPosition)
                }
                false
            }

            dragHandle.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.startDrag(this)
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_priority, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.priorityText.text = "${position + 1}. ${prioritiesList[position]}"

        if (position == selectedPosition) {
            holder.itemView.alpha = 0.5f // Resaltar opción seleccionada
        } else {
            holder.itemView.alpha = 1.0f
        }
    }

    override fun getItemCount(): Int = prioritiesList.size

    private fun toggleSelection(position: Int) {
        selectedPosition = if (selectedPosition == position) null else position
        notifyDataSetChanged()
    }
}
