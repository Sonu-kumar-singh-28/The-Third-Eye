package com.thethirdeye.esport.thethirdeye.AdminAdaptor

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thethirdeye.esport.thethirdeye.R
import com.thethirdeye.esport.thethirdeye.databinding.TournamentItemBinding
import com.thethirdeye.esport.thethirdeye.model.TournamentModel
import com.thethirdeye.esport.thethirdeye.team.CreateTeamActivity

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
        holder.binding.tvTournamentPrize.text = "₹${model.prize}"
        holder.binding.tvEntryFee.text = "Entry ₹${model.entry}"

        Glide.with(holder.itemView.context)
            .load(model.image)
            .into(holder.binding.imgTournament)

        holder.binding.cardTournament.setOnClickListener {

            val context = holder.itemView.context

            val intent = Intent(
                context,
                com.thethirdeye.esport.thethirdeye.team.CreateTeamActivity::class.java
            )

            intent.putExtra("tournamentId", model.title) // (better: doc.id use karo)
            intent.putExtra("tournamentTitle", model.title)
            intent.putExtra("tournamentGame", model.game)
            intent.putExtra("tournamentPrize", model.prize)

            context.startActivity(intent)
        }
    }
}