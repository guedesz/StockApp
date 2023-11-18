package com.example.stockapp.ui.receita

import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockapp.data.objects.Receita

import com.example.stockapp.data.repositories.CategoryRepositorySQlite
import com.example.stockapp.databinding.FragmentReceitasBinding
import com.example.stockapp.ui.CategoryViewModel
import com.example.stockapp.ui.ReceitasViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class ReceitasFragment : Fragment() {

    private var _binding: FragmentReceitasBinding? = null
    private val binding get() = _binding!!
    private val args: ReceitasFragmentArgs by navArgs()
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceitasBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.categoryName.text = args.categoryName


        val viewModel : ReceitasViewModel by activityViewModels()
        val categoryViewModel : CategoryViewModel by activityViewModels()

        val searchView = binding.search

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (query == null || query == "") {
                    return false;
                }

                lifecycleScope.launch {

                    val categoryIdFragment = categoryViewModel.getByName(args.categoryName)

                    val receitasFiltradas = viewModel.receitas.value.filter { it.category_id == categoryIdFragment.id }
                    val receitas = receitasFiltradas.filter { it.name.lowercase().contains(query.lowercase()) }

                    // Create a new adapter with the filtered data and set it on the RecyclerView
                    val recyclerView = binding.receitasRecycler
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = ReceitasAdapter(receitas, viewModel)


                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (newText == null) {
                   return false;
                }


                lifecycleScope.launch {

                    val categoryIdFragment = categoryViewModel.getByName(args.categoryName)

                    val receitasFiltradas = viewModel.receitas.value.filter { it.category_id == categoryIdFragment.id }
                    val receitas = receitasFiltradas.filter { it.name.lowercase().contains(newText.lowercase()) }

                    // Create a new adapter with the filtered data and set it on the RecyclerView
                    val recyclerView = binding.receitasRecycler
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = ReceitasAdapter(receitas, viewModel)


                }

                return true
            }

        })

        lifecycleScope.launch {

            val categoryIdFragment = categoryViewModel.getByName(args.categoryName)

            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.receitas.collect{ receitas ->

                    val receitasFiltradas = receitas.filter { it.category_id == categoryIdFragment.id }.filterNot { it.isDeleted }

                    if (binding.receitasRecycler is RecyclerView) {
                        val recyclerView = binding.receitasRecycler
                        with(recyclerView) {
                            layoutManager = LinearLayoutManager(context)
                            adapter = ReceitasAdapter(receitasFiltradas,viewModel)
                        }
                    }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}