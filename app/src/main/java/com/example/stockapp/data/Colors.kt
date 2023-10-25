package com.example.stockapp.data

class Colors {
    companion object {
        private val mapOfColors = mapOf(
            "Purple" to "#9C27B0",
            "Dark Blue" to "#3F51B5",
            "Pink" to "#E91E63",
            "Green" to "#4CAF50"
        )

        fun get(key: String): String {
            return mapOfColors[key] ?: "#9C27B0" // Retorna "#9C27B0" como valor padrão se a cor não for encontrada
        }
    }
}
