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

import com.example.stockapp.data.repositories.CategoryRepositorySQlite
import com.example.stockapp.databinding.FragmentReceitasBinding
import com.example.stockapp.ui.CategoryViewModel
import com.example.stockapp.ui.ReceitasViewModel
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


        binding.categoryName.text = args.categoryName

        val viewModel : ReceitasViewModel by activityViewModels()
        val categoryViewModel : CategoryViewModel by activityViewModels()

        lifecycleScope.launch {

            val categoryIdFragment = categoryViewModel.getByName(args.categoryName)

            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.receitas.collect{ receitas ->

                    val receitasFiltradas = receitas.filter { it.category_id == categoryIdFragment.id }

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