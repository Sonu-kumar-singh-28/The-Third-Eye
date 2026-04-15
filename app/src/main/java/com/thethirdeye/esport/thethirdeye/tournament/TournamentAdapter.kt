package com.thethirdeye.esport.thethirdeye.tournament

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thethirdeye.esport.thethirdeye.R
import com.thethirdeye.esport.thethirdeye.databinding.TournamentItemBinding
import com.thethirdeye.esport.thethirdeye.model.TournamentModel

class TournamentAdapter(private val list: ArrayList<TournamentModel>) :
    RecyclerView.Adapter<TournamentAdapter.ViewHolder>() {

    class ViewHolder(val binding: TournamentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = TournamentItemBinding.inflate(
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
        holder.binding.tvTournamentPrize.text = "Prize ₹${model.prize}"
        holder.binding.tvEntryFee.text = "Entry ₹${model.entry}"
        holder.binding.tvDescription.text = model.description

        Glide.with(holder.itemView.context)
            .load(model.image)
            .placeholder(R.drawable.bgmi_logo)
            .into(holder.binding.imgTournament)
    }
}