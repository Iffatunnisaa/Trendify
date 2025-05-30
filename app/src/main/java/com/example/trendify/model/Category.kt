package com.example.trendify.model

data class Category(
    val id: String = "",
    val name: String = "",
    val iconUrl: String = ""
)

data class ProductItem(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val rating: Double = 0.0
)