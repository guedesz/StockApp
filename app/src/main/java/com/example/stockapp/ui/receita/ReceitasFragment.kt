package com.example.stockapp.ui.receita

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockapp.databinding.FragmentHomeBinding
import com.example.stockapp.databinding.FragmentReceitasBinding
import com.example.stockapp.ui.CategoryViewModel
import com.example.stockapp.ui.categories.CategoryAdapter
import kotlinx.coroutines.launch

class ReceitasFragment : Fragment() {

    private var _binding: FragmentReceitasBinding? = null
    private val binding get() = _binding!!
    private val args: ReceitasFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceitasBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        println("Opening category: " + args.categoryName)

        binding.categoryName.text = args.categoryName
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}