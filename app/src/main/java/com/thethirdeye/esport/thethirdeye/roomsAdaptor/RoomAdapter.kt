package com.thethirdeye.esport.thethirdeye.roomsAdaptor

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.thethirdeye.esport.thethirdeye.databinding.ItemRoomBinding
import com.thethirdeye.esport.thethirdeye.model.Room

class RoomAdapter(
    private val roomList: List<Room>
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(val binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoomViewHolder(binding)
    }

    override fun getItemCount() = roomList.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {

        val room = roomList[position]

        holder.binding.tvRoomName.text = room.name
        holder.binding.tvRoomCode.text = "Room ID: ${room.roomCode}"

        if (room.imageUrl.isNotEmpty()) {
            holder.binding.imgRoom.setImageURI(Uri.parse(room.imageUrl))
        }

        holder.binding.btnJoinRoom.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Joining ${room.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}