package com.example.stockapp.ui.products.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.stockapp.databinding.FragmentProductItemBinding


class ProductItemFragment : Fragment() {

    private var _binding: FragmentProductItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductItemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val homeViewModel = ViewModelProvider(this).get(ProductItemViewModel::class.java)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}