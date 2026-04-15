package com.thethirdeye.esport.thethirdeye.AdminAdaptor


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thethirdeye.esport.thethirdeye.databinding.ItemNotificationBinding
import com.thethirdeye.esport.thethirdeye.model.NotificationModel

class NotificationAdapter(
    private val list: ArrayList<NotificationModel>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]

        holder.binding.tvTitle.text = model.title
        holder.binding.tvMessage.text = model.message
        holder.binding.tvTime.text = "${model.date} | ${model.time}"
    }
}