package com.thethirdeye.esport.thethirdeye.AdminAdaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thethirdeye.esport.thethirdeye.databinding.ItemStreamBinding
import com.thethirdeye.esport.thethirdeye.model.StreamModel

class StreamAdapter(
    private val list: List<StreamModel>,
    private val onClick: (StreamModel) -> Unit
) : RecyclerView.Adapter<StreamAdapter.VH>() {

    inner class VH(val binding: ItemStreamBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemStreamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val item = list[position]

        holder.binding.tvTitle.text = item.title

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = list.size
}