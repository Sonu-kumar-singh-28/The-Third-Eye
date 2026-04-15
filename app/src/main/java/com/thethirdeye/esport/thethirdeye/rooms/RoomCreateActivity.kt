package com.thethirdeye.esport.thethirdeye.rooms

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.thethirdeye.esport.thethirdeye.databinding.ActivityRoomCreateBinding
import com.thethirdeye.esport.thethirdeye.model.Room
import com.thethirdeye.esport.thethirdeye.roomsAdaptor.RoomAdapter

class RoomCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomCreateBinding
    private lateinit var adapter: RoomAdapter
    private val roomList = ArrayList<Room>()

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            binding = ActivityRoomCreateBinding.inflate(layoutInflater)
            setContentView(binding.root)
        } catch (e: Exception) {
            Toast.makeText(this, "Binding Error: ${e.message}", Toast.LENGTH_LONG).show()
            return
        }

        setupRecycler()

        binding.fabRefresh.setOnClickListener {
            loadRooms()
        }

        loadRooms()
    }

    private fun setupRecycler() {
        adapter = RoomAdapter(roomList)
        binding.recyclerRooms.layoutManager = LinearLayoutManager(this)
        binding.recyclerRooms.adapter = adapter
    }

    private fun loadRooms() {

        db.collection("rooms")
            .get()
            .addOnSuccessListener { result ->

                roomList.clear()

                var totalRooms = 0
                var activeRooms = 0

                val currentTime = System.currentTimeMillis()

                for (doc in result.documents) {

                    val name = doc.getString("name") ?: ""
                    val roomId = doc.getString("roomId") ?: ""
                    val password = doc.getString("password") ?: ""

                    val createdAt = try {
                        doc.getLong("createdAt") ?: 0L
                    } catch (e: Exception) {
                        0L
                    }

                    // COUNT TOTAL
                    totalRooms++

                    // ACTIVE (24 HOURS LOGIC)
                    val diff = currentTime - createdAt
                    val hours24 = 24 * 60 * 60 * 1000

                    if (diff <= hours24) {
                        activeRooms++
                    }

                    val room = Room(
                        id = doc.id,
                        name = name,
                        roomCode = roomId,
                        password = password,
                        createdAt = createdAt
                    )

                    roomList.add(room)
                }

                adapter.notifyDataSetChanged()

                // 🔥 SET DATA IN HEADER
                binding.tvTotalRooms.text = totalRooms.toString()
                binding.tvActiveRooms.text = activeRooms.toString()

                // EMPTY STATE
                binding.tvEmpty.visibility =
                    if (roomList.isEmpty()) View.VISIBLE else View.GONE
            }

            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}