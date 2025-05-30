package com.example.trendify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PembayaranBerhasilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran_berhasil)

        val btnInfoPesanan = findViewById<Button>(R.id.btnInfoPesanan)
        val btnBackHome = findViewById<Button>(R.id.btnBackHome)

        btnInfoPesanan.setOnClickListener {
            // Navigate to order info screen
            val intent = Intent(this, InfoPesananActivity::class.java)
            startActivity(intent)
        }

        btnBackHome.setOnClickListener {
            // Navigate back to home screen
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
        }
    }
}