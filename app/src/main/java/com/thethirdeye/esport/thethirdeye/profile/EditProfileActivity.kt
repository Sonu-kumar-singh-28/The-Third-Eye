package com.thethirdeye.esport.thethirdeye.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thethirdeye.esport.thethirdeye.auth.LoginActivity
import com.thethirdeye.esport.thethirdeye.databinding.ActivityEditProfileBinding
import com.thethirdeye.esport.thethirdeye.databinding.DialogEditProfileBinding
import com.thethirdeye.esport.thethirdeye.main.MainActivity

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserProfile()

        // 🔥 Edit button click → Dialog open
        binding.btnEditProfile.setOnClickListener {
            openEditDialog()
        }

        binding.btnLogout.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->

                    // 🔥 LOGOUT
                    auth.signOut()

                    // 🔥 Login screen pe bhejo
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

    }

    private fun loadUserProfile() {
        val uid = auth.currentUser?.uid ?: return

        db.collection("users").document(uid).get()
            .addOnSuccessListener {
                binding.tvUid.text = "UID: ${uid.take(10)}"
                binding.tvUsername.text = it.getString("name") ?: "Player"
                binding.tvEmail.text = it.getString("email") ?: ""
            }
    }

    private fun openEditDialog() {

        val dialogBinding = DialogEditProfileBinding.inflate(LayoutInflater.from(this))

        dialogBinding.etName.setText(binding.tvUsername.text.toString())
        dialogBinding.etEmail.setText(binding.tvEmail.text.toString())

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSave.setOnClickListener {

            val name = dialogBinding.etName.text.toString().trim()
            val email = dialogBinding.etEmail.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = auth.currentUser?.uid ?: return@setOnClickListener

            db.collection("users").document(uid)
                .update(
                    mapOf(
                        "name" to name,
                        "email" to email
                    )
                )
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    loadUserProfile()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }

        dialog.show()
    }
}
