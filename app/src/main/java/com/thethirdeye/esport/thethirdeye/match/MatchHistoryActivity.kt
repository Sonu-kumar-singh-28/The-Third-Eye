package com.thethirdeye.esport.thethirdeye.match

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.thethirdeye.esport.thethirdeye.databinding.ActivityMatchHistoryBinding
import com.thethirdeye.esport.thethirdeye.matchadaptor.TeamAdapter
import com.thethirdeye.esport.thethirdeye.model.Team
import com.thethirdeye.esport.thethirdeye.team.TeamDetailsActivity

class MatchHistoryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMatchHistoryBinding.inflate(layoutInflater)
    }

    private val db = FirebaseFirestore.getInstance()
    private val list = ArrayList<Team>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.rvTeams.layoutManager = LinearLayoutManager(this)

        loadTeams()
    }

    private fun loadTeams() {

        db.collection("teams")
            .get()
            .addOnSuccessListener { result ->

                list.clear()

                for (doc in result) {

                    val team = Team(
                        teamName = doc.getString("teamName") ?: "",
                        leaderId = doc.getString("leaderId") ?: "",
                        member1 = doc.getString("member1") ?: "",
                        member2 = doc.getString("member2") ?: "",
                        member3 = doc.getString("member3") ?: "",
                        member4 = doc.getString("member4") ?: "",
                        teamType = doc.getString("teamType") ?: ""
                    )

                    list.add(team)
                }

                val adapter = TeamAdapter(list) { team ->

                    val intent = Intent(this, TeamDetailsActivity::class.java)

                    intent.putExtra("teamName", team.teamName)
                    intent.putExtra("leaderId", team.leaderId)
                    intent.putExtra("member1", team.member1)
                    intent.putExtra("member2", team.member2)
                    intent.putExtra("member3", team.member3)
                    intent.putExtra("member4", team.member4)
                    intent.putExtra("teamType", team.teamType)

                    startActivity(intent)
                }

                binding.rvTeams.adapter = adapter
            }
    }
}