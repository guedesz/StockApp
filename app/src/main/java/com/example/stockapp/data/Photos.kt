package com.example.stockapp.data

import com.example.stockapp.R

class Photos{
    companion object {
        fun get(key: String): Int {
            val mapOfPhotos = mapOf(
                "zerolactose.jpg" to R.drawable.vegetariano,
                "glutenfree.jpg" to R.drawable.semglutem,
                "vegetariano.jpg" to R.drawable.vegetariano,
            )

            return mapOfPhotos[key] ?: R.drawable.semfoto
        }
    }
}
