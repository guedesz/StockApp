package com.example.stockapp.data.objects

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.stockapp.data.objects.Category

@Entity(
    tableName = "receitas",
)

data class Receita(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var docId: String,
    var name: String,
    var ingredientes: String,
    var modo_preparo: String,
    var calories: Int,
    var likes: Int,
    var photo: String,
    var category_id: Int,
    var isSynced: Boolean,
    var isDeleted: Boolean
)

{
    constructor():this(
        0,
        "",
        "",
        "",
        "",
        0,
        0,
        "semfoto.jpg",
        0,
        false,
        false
    )
}
