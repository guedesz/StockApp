package com.example.stockapp.data

import com.example.stockapp.R

class Photos{
    companion object {
        fun get(key: String): Int {
            val mapOfPhotos = mapOf(
                "zerolactose.jpg" to R.drawable.vegetariano,
                "glutenfree.jpg" to R.drawable.semglutem,
                "vegetariano.jpg" to R.drawable.vegetariano,
                "bolofuba" to R.drawable.bolo_fuba,
                "paobanana" to R.drawable.paobanana,
                "panquecasaveia" to R.drawable.panquecaaveia,
                "tortafrango" to R.drawable.tortafrango,
                "browniebatatadoce" to R.drawable.browniebatatadoce,
                "saladaquinoa" to R.drawable.saladaquinoa,
                "currygraodebico" to R.drawable.currygrao,
                "lasanhaberinjela" to R.drawable.lasanhaberinjela,
                "hamburguerfeijaopreto" to R.drawable.hamburguerfeijao,
                "tacoscouveflor" to R.drawable.tacocouveflor,
                "risotocogumelos" to R.drawable.risotocogumelo,
                "saladaabacatequinoa" to R.drawable.saladaabacate
            )

            return mapOfPhotos[key] ?: R.drawable.semfoto
        }
    }
}
