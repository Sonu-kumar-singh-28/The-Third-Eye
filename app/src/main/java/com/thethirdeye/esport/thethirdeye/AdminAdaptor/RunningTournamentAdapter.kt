package com.thethirdeye.esport.thethirdeye.AdminAdaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thethirdeye.esport.thethirdeye.R
import com.thethirdeye.esport.thethirdeye.databinding.ItemRunningTournamentBinding
import com.thethirdeye.esport.thethirdeye.model.TournamentModel

class RunningTournamentAdapter(
    private val list: ArrayList<TournamentModel>
) : RecyclerView.Adapter<RunningTournamentAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRunningTournamentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemRunningTournamentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]

        holder.binding.tvTournamentName.text = model.title
        holder.binding.tvTournamentType.text = model.game
        holder.binding.tvTournamentPrize.text = "₹${model.prize}"
        holder.binding.tvEntryFee.text = "Entry ₹${model.entry}"

        Glide.with(holder.itemView.context)
            .load(model.image)
            .placeholder(R.drawable.bgmi_logo)
            .into(holder.binding.imgTournament)
    }
}