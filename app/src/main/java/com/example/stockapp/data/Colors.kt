package com.example.stockapp.data

class Colors{
    companion object {
        fun get(key: String): Comparable<Nothing> {
            val mapOfColors = mapOf(
                "Purple" to "#9C27B0",
                "Dark Blue" to "#3F51B5",
                "Pink" to "#E91E63",
                "Green" to "#4CAF50"
            )

            return mapOfColors[key] ?: "#9C27B0"
        }
    }
}
