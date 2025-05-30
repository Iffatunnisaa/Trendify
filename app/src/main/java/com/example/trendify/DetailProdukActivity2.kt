package com.example.trendify

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.NumberFormat
import java.util.Locale

class DetailProdukActivity2 : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnCart: ImageButton
    private lateinit var btnIncrease: ImageButton
    private lateinit var btnDecrease: ImageButton
    private lateinit var btnAddToCart: Button
    private lateinit var tvQuantity: TextView
    private lateinit var tvTotal: TextView

    private var quantity = 1
    private val pricePerItem = 210000

    // Data produk dummy
    private val selectedProduct = Product(
        id = "productId2",
        name = "Long Dress",
        price = 210000,
        imageUrl = "url_to_image"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailproduk2)

        // Inisialisasi View
        btnBack = findViewById(R.id.btn_back2)
        btnCart = findViewById(R.id.btn_cart2)
        btnIncrease = findViewById(R.id.btnIncrease2)
        btnDecrease = findViewById(R.id.btnDecrease2)
        btnAddToCart = findViewById(R.id.btnAddToCart2)
        tvQuantity = findViewById(R.id.tvQuantity2)
        tvTotal = findViewById(R.id.tvTotal2)

        // Set quantity awal
        updateQuantityDisplay()

        // Event untuk kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Tambah jumlah produk
        btnIncrease.setOnClickListener {
            quantity++
            updateQuantityDisplay()
        }

        // Kurangi jumlah produk
        btnDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityDisplay()
            }
        }

        // Tambah ke keranjang
        btnAddToCart.setOnClickListener {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener
            val productId = selectedProduct.id

            val productToCart = mapOf(
                "name" to selectedProduct.name,
                "price" to selectedProduct.price,
                "quantity" to quantity,
                "image" to selectedProduct.imageUrl
            )

            val cartRef = FirebaseDatabase.getInstance().getReference("cart")
            cartRef.child(userId).child(productId).setValue(productToCart)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                    // Navigate to KeranjangActivity
                    val intent = Intent(this, KeranjangActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal menambahkan: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateQuantityDisplay() {
        tvQuantity.text = quantity.toString()
        val totalPrice = quantity * pricePerItem
        tvTotal.text = "Rp${formatRupiah(totalPrice)}"
    }

    private fun formatRupiah(amount: Int): String {
        val format = NumberFormat.getInstance(Locale("in", "ID"))
        return format.format(amount)
    }

    data class Product(
        val id: String,
        val name: String,
        val price: Int,
        val imageUrl: String
    )
}
