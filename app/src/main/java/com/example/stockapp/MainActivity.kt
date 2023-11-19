package com.example.stockapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.stockapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        // Configurações do Firestore
        val firestore = FirebaseFirestore.getInstance()

        // Desativa o cache
        val settings = FirebaseFirestoreSettings.Builder()
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .setPersistenceEnabled(false)
            .build()


        firestore.firestoreSettings = settings
        FirebaseFirestore.getInstance().clearPersistence()
            .addOnSuccessListener {
                // Cache foi limpo com sucesso
                println("CACHED CLEARED")
            }
            .addOnFailureListener {
                println("CACHED CLEAR FAIL")
            }


        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(_binding.fragmentHome.id) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(_binding.bottomView, navController)
    }
}