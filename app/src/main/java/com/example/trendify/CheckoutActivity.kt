package com.example.trendify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trendify.model.CartItem
import com.google.firebase.database.FirebaseDatabase
import java.text.NumberFormat
import java.util.*

class CheckoutActivity : AppCompatActivity() {

    private lateinit var tvAddress: TextView
    private lateinit var tvShippingOption: TextView
    private lateinit var tvShippingCost: TextView
    private lateinit var tvProductTotal: TextView
    private lateinit var tvServiceFee: TextView
    private lateinit var tvTotalPayment: TextView
    private lateinit var btnPayment: Button

    private var cartItems: List<CartItem> = listOf()
    private val shippingCost = 5000
    private val serviceFee = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Ambil data dari intent
        cartItems = intent.getParcelableArrayListExtra<CartItem>("cartItems") ?: listOf()

        // Initialize views
        tvAddress = findViewById(R.id.tvAddress)
        tvShippingOption = findViewById(R.id.tvOpsiPengiriman)
        tvShippingCost = findViewById(R.id.tvOngkir)
        tvProductTotal = findViewById(R.id.tvProductTotal)
        tvServiceFee = findViewById(R.id.tvServiceFee)
        tvTotalPayment = findViewById(R.id.tvTotalPayment)
        btnPayment = findViewById(R.id.btn_pembayaran)

        // Display checkout details
        displayCheckoutDetails()

        // Handle payment button click
        btnPayment.setOnClickListener {
            recordPaymentToFirebase() // Call the function to record payment
        }
    }

    private fun displayCheckoutDetails() {
        // Address details
        tvAddress.text = "Trendify’s (+62) 813-0000-0000\nJln. Fatahillah IV Geuceu Iniem No. 13, Banda Aceh, NAD\n81063"

        // Shipping option
        tvShippingOption.text = "Hemat\nGaransi tiba 16 - 19 April"
        tvShippingCost.text = formatCurrency(shippingCost)

        // Product total
        val productTotal = cartItems.sumOf { it.price * it.quantity }
        tvProductTotal.text = formatCurrency(productTotal)

        // Service fee
        tvServiceFee.text = formatCurrency(serviceFee)

        // Total payment
        val totalPayment = productTotal + shippingCost + serviceFee
        tvTotalPayment.text = formatCurrency(totalPayment)
    }

    private fun formatCurrency(amount: Int): String {
        return "Rp${NumberFormat.getInstance(Locale("in", "ID")).format(amount)}"
    }

    private fun recordPaymentToFirebase() {
        val database = FirebaseDatabase.getInstance().getReference("payments")
        val paymentId = database.push().key ?: return

        val paymentDetails = mapOf(
            "id" to paymentId,
            "items" to cartItems.map { it.name },
            "total" to tvTotalPayment.text.toString(),
            "status" to "Pesanan sedang diproses",
            "timestamp" to System.currentTimeMillis()
        )

        // Log the payment details for debugging
        println("Payment Details: $paymentDetails")

        database.child(paymentId).setValue(paymentDetails).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Pembayaran berhasil tercatat!", Toast.LENGTH_SHORT).show()
                navigateToInfoPesananScreen(paymentId)
            } else {
                Toast.makeText(this, "Gagal mencatat pembayaran: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                println("Firebase Error: ${task.exception?.message}")
            }
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Gagal mencatat pembayaran: ${error.message}", Toast.LENGTH_SHORT).show()
            println("Firebase Failure: ${error.message}")
        }
    }

    private fun navigateToInfoPesananScreen(orderId: String) {
        val intent = Intent(this, InfoPesananActivity::class.java)
        intent.putExtra("ORDER_ID", orderId)
        startActivity(intent)
        finish()
    }
}