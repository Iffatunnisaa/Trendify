package com.example.trendify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.trendify.DetailProdukActivity
import com.example.trendify.R
import com.example.trendify.model.ProductDetail

class ProductAdapter(private val context: Context, private val productList: List<ProductDetail>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pesanan, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailProdukActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.id)
            intent.putExtra("PRODUCT_NAME", product.name)
            intent.putExtra("PRODUCT_PRICE", product.price)
            intent.putExtra("PRODUCT_IMAGE", product.imageUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: ProductDetail) {
            // Bind product data to UI elements
        }
    }
}