package com.thethirdeye.esport.thethirdeye.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thethirdeye.esport.thethirdeye.databinding.ActivityRegisterBinding
import com.thethirdeye.esport.thethirdeye.main.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.btnRegister.setOnClickListener {
            val name = binding.nametextfield.editText?.text.toString().trim()
            val email = binding.emailtextfield.editText?.text.toString().trim()
            val password = binding.passwordtextfield.editText?.text.toString().trim()
            val repeat = binding.repeattextfield.editText?.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || repeat.isEmpty()) {
                toast("All fields required")
                return@setOnClickListener
            }

            if (password != repeat) {
                toast("Passwords do not match")
                return@setOnClickListener
            }

            registerUser(name, email, password)
        }

    }

    private fun registerUser(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = auth.currentUser!!.uid

                val userMap = hashMapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to email,
                    "role" to "gamer",
                    "createdAt" to System.currentTimeMillis()
                )

                db.collection("users")
                    .document(uid)
                    .set(userMap)
                    .addOnSuccessListener {
                        toast("Registration Successful")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
            }
            .addOnFailureListener {
                toast(it.message ?: "Registration Failed")
            }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}