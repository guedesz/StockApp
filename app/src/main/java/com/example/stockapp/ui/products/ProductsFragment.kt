package com.example.stockapp.ui.products

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stockapp.R
import com.example.stockapp.databinding.FragmentHomeBinding
import com.example.stockapp.databinding.FragmentProductsBinding
import com.example.stockapp.ui.home.HomeViewModel

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val homeViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}