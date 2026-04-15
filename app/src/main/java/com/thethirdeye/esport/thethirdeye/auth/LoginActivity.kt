package com.thethirdeye.esport.thethirdeye.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thethirdeye.esport.thethirdeye.admin.AdminMainActivity
import com.thethirdeye.esport.thethirdeye.databinding.ActivityLoginBinding
import com.thethirdeye.esport.thethirdeye.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.emailtextfield.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                toast("Email & Password required")
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        binding.signupOption.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            checkUserRole(auth.currentUser!!.uid)
        }
    }

    private fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                val uid = auth.currentUser!!.uid
                checkUserRole(uid)

            }
            .addOnFailureListener {
                toast(it.message ?: "Login Failed")
            }
    }

    private fun checkUserRole(uid: String) {

        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->

                val role = doc.getString("role")

                if (role == "admin") {

                    startActivity(
                        Intent(this, AdminMainActivity::class.java)
                    )

                } else {

                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )

                }

                finish()
            }
            .addOnFailureListener {
                toast("Failed to fetch role")
            }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}