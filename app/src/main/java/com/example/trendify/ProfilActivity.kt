package com.example.trendify

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.trendify.PrivacyPolicyActivity
import com.example.trendify.LoginActivity
import com.example.trendify.R
import com.google.firebase.database.FirebaseDatabase
import java.util.Date
import java.util.Locale

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        // Tombol Kebijakan Aplikasi
        val btnPrivacyPolicy = findViewById<Button>(R.id.btnPrivacyPolicy)
        btnPrivacyPolicy.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        // Tombol Logout
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logLogoutEventToFirebase()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun logLogoutEventToFirebase() {
        val database = FirebaseDatabase.getInstance().getReference("logout_logs")
        val userId = "USER_ID" // Replace with actual user ID (e.g., from FirebaseAuth)
        val timestamp = System.currentTimeMillis()
        val formattedDate = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault()).format(
            Date(timestamp)
        )

        val logoutData = mapOf(
            "userId" to userId,
            "timestamp" to timestamp,
            "formattedDate" to formattedDate
        )

        database.push().setValue(logoutData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Logout event logged successfully.")
            } else {
                println("Failed to log logout event: ${task.exception?.message}")
            }
        }
    }
}