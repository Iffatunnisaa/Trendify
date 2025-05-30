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

class DetailProdukActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnCart: ImageButton
    private lateinit var btnIncrease: ImageButton
    private lateinit var btnDecrease: ImageButton
    private lateinit var btnAddToCart: Button
    private lateinit var tvQuantity: TextView
    private lateinit var tvTotal: TextView

    private var quantity = 2
    private val pricePerItem = 60000  // Harga satuan (Rp60.000)

    // Data produk dummy
    private val selectedProduct = Product(
        id = "productId1",
        name = "Pashmina Kaos",
        price = 60000,
        imageUrl = "url_to_image"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailproduk)

        // Inisialisasi View
        btnBack = findViewById(R.id.btn_back)
        btnCart = findViewById(R.id.btn_cart)
        btnIncrease = findViewById(R.id.btnIncrease)
        btnDecrease = findViewById(R.id.btnDecrease)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        tvQuantity = findViewById(R.id.tvQuantity)
        tvTotal = findViewById(R.id.tvTotal)

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
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId == null) {
                Toast.makeText(this, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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
