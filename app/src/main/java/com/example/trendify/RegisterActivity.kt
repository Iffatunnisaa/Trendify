package com.example.trendify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trendify.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            val isAgreed = binding.cbAgree.isChecked

            // Validate inputs
            if (name.isEmpty()) {
                binding.etName.error = "Name cannot be empty"
                binding.etName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.etEmail.error = "Email cannot be empty"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password cannot be empty"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty() || password != confirmPassword) {
                binding.etConfirmPassword.error = "Passwords do not match"
                binding.etConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            if (!isAgreed) {
                Toast.makeText(this, "You must agree to the terms and conditions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register user
            registerUser(email, password)
        }

        binding.tvLoginLink.setOnClickListener {
            // Navigate to LoginActivity (if implemented)
            finish()
        }
    }

    private fun registerUser(email: String, password: String) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val error = task.exception
                    if (error is FirebaseNetworkException) {
                        Toast.makeText(this, "Network error. Check connection.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Error: ${error?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }


    // Add this helper function
    @SuppressLint("ServiceCast")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}