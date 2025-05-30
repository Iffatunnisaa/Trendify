package com.example.trendify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class BajuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_baju) // sesuaikan dengan nama layout XML celana
    }

    // Klik produk celana 1
    fun onClickBaju1(view: View) {
        val intent = Intent(this, DetailProdukActivity::class.java)
        intent.putExtra("PRODUCT_NAME", "One Set Vest")
        intent.putExtra("PRODUCT_PRICE", 198000)
        startActivity(intent)
    }

    // Navigate to Home
    fun onHomeClick(view: View) {
        val intent = Intent(this, BerandaActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close the current activity
    }

    // Navigate to Keranjang
    fun onCategoryClick(view: View) {
        val intent = Intent(this, KeranjangActivity::class.java) // Replace with your KategoriActivity
        startActivity(intent)
        finish() // Optional: Close the current activity
    }

    // Navigate to Kamera/Info Pesanan
    fun onInfoClick(view: View) {
        val intent = Intent(this, InfoPesananActivity::class.java) // Replace with your com.example.trendify.InfoPesananActivity
        startActivity(intent)
        finish() // Optional: Close the current activity
    }

    // Navigate to Profil
    fun onProfileClick(view: View) {
        val intent = Intent(this, ProfilActivity::class.java) // Replace with your ProfilActivity
        startActivity(intent)
        finish() // Optional: Close the current activity
    }
}