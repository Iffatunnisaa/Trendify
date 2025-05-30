package com.example.trendify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendify.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.NumberFormat
import java.util.*

class KeranjangActivity : AppCompatActivity() {

    private lateinit var rvCartItems: RecyclerView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnCheckout: Button


    private lateinit var database: DatabaseReference
    private lateinit var cartAdapter: CartAdapter
    private var cartItems = mutableListOf<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        rvCartItems = findViewById(R.id.rvCartItems)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnCheckout = findViewById(R.id.btnCheckout)

        rvCartItems.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(cartItems,
            onQuantityChanged = { updateTotalPrice() },
            onItemRemoved = { updateTotalPrice() })
        rvCartItems.adapter = cartAdapter

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        database = FirebaseDatabase.getInstance().getReference("cart").child(userId)

        fetchCartItems()

        btnCheckout.setOnClickListener {
            val selectedItems = cartItems.filter { it.selected } // Filter selected items
            if (selectedItems.isNotEmpty()) {
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putParcelableArrayListExtra("cartItems", ArrayList(selectedItems))
                startActivity(intent)
            } else {
                Toast.makeText(this, "Pilih item untuk checkout", Toast.LENGTH_SHORT).show()
            }
        }

        btnCheckout.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putParcelableArrayListExtra("cartItems", ArrayList(cartItems))
            startActivity(intent)
        }
    }

    private fun fetchCartItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CartItem::class.java)
                    item?.let {
                        it.id = itemSnapshot.key ?: ""
                        cartItems.add(it)
                    }
                }
                cartAdapter.notifyDataSetChanged()
                updateTotalPrice()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@KeranjangActivity, "Gagal memuat keranjang", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTotalPrice() {
        val total = cartItems.sumOf { it.price * it.quantity }
        val formatted = NumberFormat.getInstance(Locale("in", "ID")).format(total)
        tvTotalPrice.text = "Total: Rp$formatted"
    }

}
