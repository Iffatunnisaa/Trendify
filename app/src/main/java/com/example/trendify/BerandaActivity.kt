package com.example.trendify

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trendify.databinding.ActivityBerandaBinding
import com.google.firebase.database.*

class BerandaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var categoryRef: DatabaseReference
    private lateinit var flashSaleRef: DatabaseReference
    private lateinit var popularProductsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        categoryRef = database.getReference("categories")
        flashSaleRef = database.getReference("flashSale")
        popularProductsRef = database.getReference("popularProducts")

        // Fetch and display data
        fetchCategories()
        fetchFlashSale()
        fetchPopularProducts()

        // Set click listeners for categories
        val categoryBaju = findViewById<LinearLayout>(R.id.kategori_baju)
        val categoryCelana = findViewById<LinearLayout>(R.id.kategori_celana)

        categoryBaju.setOnClickListener {
            val intent = Intent(this, BajuActivity::class.java)
            intent.putExtra("CATEGORY_NAME", "Baju")
            startActivity(intent)
        }

        categoryCelana.setOnClickListener {
            val intent = Intent(this, CelanaActivity::class.java)
            intent.putExtra("CATEGORY_NAME", "Celana")
            startActivity(intent)
        }
    }

    // Navigate to Home
    fun onHomeClick(view: View) {
        val intent = Intent(this, BerandaActivity::class.java)
        startActivity(intent)
    }

    // Navigate to Keranjang
    fun onCategoryClick(view: View) {
        val intent = Intent(this, KeranjangActivity::class.java)
        startActivity(intent)
    }

    // Navigate to Kamera/Info Pesanan
    fun onInfoClick(view: View) {
        val intent = Intent(this, InfoPesananActivity::class.java)
        startActivity(intent)
    }

    // Navigate to Profil
    fun onProfileClick(view: View) {
        val intent = Intent(this, ProfilActivity::class.java)
        startActivity(intent)
    }

    private fun fetchCategories() {
        categoryRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Handle category data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@BerandaActivity, "Failed to load categories: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchFlashSale() {
        flashSaleRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Handle flash sale data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@BerandaActivity, "Failed to load flash sale items: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchPopularProducts() {
        popularProductsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Handle popular products data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@BerandaActivity, "Failed to load popular products: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun onProductClick1(view: View) {
        val intent = Intent(this, DetailProdukActivity::class.java)
        intent.putExtra("NAMA_PRODUK", "One Set Vest")
        intent.putExtra("HARGA_PRODUK", "Rp198.000")
        intent.putExtra("GAMBAR_PRODUK", R.drawable.product1)
        startActivity(intent)
    }

    fun onProductClick2(view: View) {
        val intent = Intent(this, DetailProdukActivity2::class.java)
        intent.putExtra("NAMA_PRODUK", "Long Dress")
        intent.putExtra("HARGA_PRODUK", "Rp210.000")
        intent.putExtra("GAMBAR_PRODUK", R.drawable.product2)
        startActivity(intent)
    }
}