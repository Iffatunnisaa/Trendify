package com.example.trendify.model

data class Product(
    val categories: List<CategoryX>,
    val flashSale: List<FlashSale>,
    val popularProducts: List<PopularProduct>
)