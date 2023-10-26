package com.example.stockapp

import android.R
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.stockapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(_binding.fragmentHome.id) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(_binding.bottomView, navController)
    }
}