package com.example.trendify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class InfoPesananActivity : AppCompatActivity() {

    private lateinit var tvOrderId: TextView
    private lateinit var tvOrderItems: TextView
    private lateinit var tvOrderTotal: TextView
    private lateinit var tvOrderTimestamp: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_pesanan)

        // Initialize views
        tvOrderId = findViewById(R.id.tvOrderId)
        tvOrderItems = findViewById(R.id.tvOrderItems)
        tvOrderTotal = findViewById(R.id.tvOrderTotal)
        tvOrderTimestamp = findViewById(R.id.tvOrderTimestamp)
        btnBack = findViewById(R.id.btnBack1)

        // Retrieve order ID from intent
        val orderId = intent.getStringExtra("ORDER_ID") ?: return

        // Fetch order details from Firebase
        fetchOrderDetails(orderId)

        // Set click listener for the Back button
        btnBack.setOnClickListener {
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
    }

    private fun fetchOrderDetails(orderId: String) {
        val database = FirebaseDatabase.getInstance().getReference("payments").child(orderId)

        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val items = snapshot.child("items").value as? List<*>
                val total = snapshot.child("total").value as? String
                val timestamp = snapshot.child("timestamp").value as? Long

                // Debugging logs
                println("Items: $items")
                println("Total: $total")
                println("Timestamp: $timestamp")

                // Display data
                tvOrderId.text = "Order ID: $orderId"
                tvOrderItems.text = "Items: ${items?.joinToString(", ") ?: "No items found"}"
                tvOrderTotal.text = "Total: ${total ?: "Unknown"}"
                tvOrderTimestamp.text = "Timestamp: ${timestamp?.let { formatTimestamp(it) } ?: "Unknown"}"
            } else {
                Toast.makeText(this, "Order not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Failed to fetch order: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val date = java.util.Date(timestamp)
        val format = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale.getDefault())
        return format.format(date)
    }
}