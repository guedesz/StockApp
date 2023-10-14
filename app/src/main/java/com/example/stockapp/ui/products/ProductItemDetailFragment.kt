package com.example.stockapp.ui.products

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stockapp.R

class ProductItemDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ProductItemDetailFragment()
    }

    private lateinit var viewModel: ProductItemDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_item_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductItemDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}