package com.thethirdeye.esport.thethirdeye.team

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.thethirdeye.esport.thethirdeye.databinding.ActivityCreateTeamBinding
import com.thethirdeye.esport.thethirdeye.match.MatchHistoryActivity

class CreateTeamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTeamBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // default hide all players
        showPlayers(1)

        // TEAM TYPE CHANGE
        binding.rgTeamType.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                binding.rbSolo.id -> showPlayers(1)
                binding.rbDuo.id -> showPlayers(2)
                binding.rbSquad.id -> showPlayers(4)
            }
        }

        // REGISTER BUTTON
        binding.btnCreateTeam.setOnClickListener {
            createTeam()
        }


        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, MatchHistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showPlayers(count: Int) {

        binding.tilPlayer1.visibility = View.VISIBLE

        binding.tilPlayer2.visibility = if (count >= 2) View.VISIBLE else View.GONE
        binding.tilPlayer3.visibility = if (count >= 3) View.VISIBLE else View.GONE
        binding.tilPlayer4.visibility = if (count == 4) View.VISIBLE else View.GONE
    }

    private fun createTeam() {

        val teamName = binding.etTeamName.text.toString().trim()
        val leaderId = binding.etleaderid.text.toString().trim()

        val member1 = binding.etPlayer1.text.toString().trim()
        val member2 = binding.etPlayer2.text.toString().trim()
        val member3 = binding.etPlayer3.text.toString().trim()
        val member4 = binding.etPlayer4.text.toString().trim()

        val teamType =
            when (binding.rgTeamType.checkedRadioButtonId) {
                binding.rbSolo.id -> "Solo"
                binding.rbDuo.id -> "Duo"
                binding.rbSquad.id -> "Squad"
                else -> "Solo"
            }

        if (teamName.isEmpty() || leaderId.isEmpty()) {
            Toast.makeText(this, "Fill required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val teamData = hashMapOf(
            "teamName" to teamName,
            "leaderId" to leaderId,
            "member1" to member1,
            "member2" to member2,
            "member3" to member3,
            "member4" to member4,
            "teamType" to teamType
        )

        db.collection("teams")
            .add(teamData)
            .addOnSuccessListener {
                Toast.makeText(this, "Team Created Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }
}