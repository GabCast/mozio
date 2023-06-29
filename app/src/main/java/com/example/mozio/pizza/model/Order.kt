package com.example.mozio.pizza.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var price: Float = 0F,
    var flavors: ArrayList<String> = arrayListOf()
) : Parcelable
