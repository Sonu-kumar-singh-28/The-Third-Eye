package com.thethirdeye.esport.thethirdeye.matchadaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thethirdeye.esport.thethirdeye.databinding.ItemTeamBinding
import com.thethirdeye.esport.thethirdeye.model.Team


class TeamAdapter(
    private val list: List<Team>,
    private val onClick: (Team) -> Unit
) : RecyclerView.Adapter<TeamAdapter.VH>() {

    class VH(val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTeamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        val item = list[position]

        holder.binding.tvTeamName.text = item.teamName
        holder.binding.tvTeamType.text = item.teamType
        holder.binding.tvLeader.text = "Leader: ${item.leaderId}"

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }
}
