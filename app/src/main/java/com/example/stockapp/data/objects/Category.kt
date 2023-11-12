package com.example.stockapp.data.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var docId: String,
    var photo: String,
    var name: String,
    var color: String,
)
{
    constructor():this(
        0,
        "0",
        "semfoto.jpg",
        "Categoria",
        "Purple"
    )
}
