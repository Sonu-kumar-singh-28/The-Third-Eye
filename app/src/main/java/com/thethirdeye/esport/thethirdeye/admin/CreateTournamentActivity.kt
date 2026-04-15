package com.thethirdeye.esport.thethirdeye.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.thethirdeye.esport.thethirdeye.databinding.ActivityCreateTournamentBinding

class CreateTournamentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTournamentBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreate.setOnClickListener {

            val title = binding.etTitle.text.toString().trim()
            val game = binding.etGame.text.toString().trim()
            val prize = binding.etPrize.text.toString().trim()
            val entry = binding.etEntry.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            val tournament = HashMap<String, Any>()

            tournament["title"] = title
            tournament["game"] = game
            tournament["prize"] = prize
            tournament["entry"] = entry
            tournament["description"] = description
            tournament["image"] = ""

            db.collection("tournaments")
                .add(tournament)
                .addOnSuccessListener {

                    Toast.makeText(this, "Tournament Created", Toast.LENGTH_SHORT).show()

                    // All Tournament Page open
                    startActivity(
                        Intent(this, AllTournamentActivity::class.java)
                    )

                    finish() // current activity close
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}