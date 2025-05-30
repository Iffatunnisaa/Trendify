package com.example.trendify

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trendify.R

class PrivacyPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kebijakan_privasi)

        // Set content for the privacy policy sections
        val tvCancellationPolicyContent = findViewById<TextView>(R.id.tvCancellationPolicyContent)
        tvCancellationPolicyContent.text = "Pengguna memiliki hak untuk membatalkan pesanan selama status pesanan belum masuk ke tahap pengiriman. Jika pesanan sudah dalam proses pengiriman, maka pembatalan tidak dapat dilakukan. Untuk proses pembatalan, pengguna dapat mengakses halaman \"Pesanan Saya\" dan memilih opsi pembatalan yang tersedia. Dana akan dikembalikan sesuai metode pembayaran dalam waktu 3–5 hari kerja setelah pembatalan disetujui."

        val tvTermsConditionsContent = findViewById<TextView>(R.id.tvTermsConditionsContent)
        tvTermsConditionsContent.text = "Dengan menggunakan aplikasi ini, pengguna dianggap telah membaca, memahami, dan menyetujui semua ketentuan yang berlaku. Pengguna bertanggung jawab atas keakuratan informasi yang diberikan saat melakukan transaksi, termasuk nama, alamat, dan metode pembayaran.\n\nKami berhak mengubah atau menghentikan layanan kapan saja tanpa pemberitahuan terlebih dahulu. Pengguna dilarang menyalahgunakan layanan, termasuk melakukan tindakan penipuan atau pelanggaran hukum lainnya."

        val tvPrivacyDataProtectionContent = findViewById<TextView>(R.id.tvPrivacyDataProtectionContent)
        tvPrivacyDataProtectionContent.text = "Kami berkomitmen untuk menjaga kerahasiaan dan keamanan data pribadi Anda. Informasi seperti nama, email, alamat, dan data transaksi disimpan dengan aman dan hanya digunakan untuk keperluan pelayanan, pengiriman, dan peningkatan pengalaman pengguna.\n\nKami tidak akan membagikan data Anda kepada pihak ketiga tanpa persetujuan kecuali diwajibkan oleh hukum. Pengguna dapat meminta penghapusan data kapan saja melalui menu \"Kontak Kami\"."
    }
}