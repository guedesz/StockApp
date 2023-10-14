package com.example.stockapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var description: String,
    var price: Double,
    var quantity: Int,
    var photo: String
)
{
    constructor():this(
        0,
        "",
        "",
        0.0,
        0,
        "semfoto.jpg",
    )
}
