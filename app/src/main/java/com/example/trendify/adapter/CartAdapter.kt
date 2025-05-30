package com.example.trendify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.trendify.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onQuantityChanged: () -> Unit,
    private val onItemRemoved: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        val btnIncrease: ImageButton = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: ImageButton = itemView.findViewById(R.id.btnDecrease)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        val cbSelect: CheckBox = itemView.findViewById(R.id.cbSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pesanan, parent, false)
        if (view.findViewById<TextView>(R.id.tvName) == null) {
            throw IllegalStateException("View ID tvName not found in item_pesanan.xml")
        }
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvPrice.text = "Rp${item.price}"
        holder.tvQuantity.text = item.quantity.toString()
        holder.cbSelect.isChecked = item.selected

        holder.btnIncrease.setOnClickListener {
            item.quantity++
            updateQuantityInFirebase(item.id, item.quantity)
            onQuantityChanged()
            notifyItemChanged(position)
        }

        holder.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                updateQuantityInFirebase(item.id, item.quantity)
                onQuantityChanged()
                notifyItemChanged(position)
            }
        }

        holder.btnDelete.setOnClickListener {
            items.removeAt(position)
            removeItemFromFirebase(item.id)
            onItemRemoved()
            notifyItemRemoved(position)
        }

        holder.cbSelect.setOnCheckedChangeListener { _, isChecked ->
            item.selected = isChecked
        }
    }

    override fun getItemCount(): Int = items.size

    private fun updateQuantityInFirebase(productId: String, newQty: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val ref = FirebaseDatabase.getInstance().getReference("cart").child(userId).child(productId).child("quantity")
        ref.setValue(newQty).addOnSuccessListener { onQuantityChanged() }
    }

    private fun removeItemFromFirebase(productId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val ref = FirebaseDatabase.getInstance().getReference("cart").child(userId).child(productId)
        ref.removeValue().addOnSuccessListener { onItemRemoved() }
    }
}