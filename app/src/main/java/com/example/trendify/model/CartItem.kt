package com.example.trendify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    var id: String = "",
    var name: String = "",
    var price: Int = 0,
    var quantity: Int = 1,
    var selected: Boolean = false
) : Parcelable