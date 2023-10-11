package com.example.stockapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.stockapp.databinding.ActivityMainBinding
import com.example.stockapp.ui.products.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val viewModel : ProductViewModel by viewModels()

        val navController = findNavController(_binding.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        val bottomNav = _binding.bottomView
        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(_binding.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}