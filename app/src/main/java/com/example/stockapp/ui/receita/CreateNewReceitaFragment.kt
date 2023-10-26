package com.example.stockapp.ui.receita

import android.R
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stockapp.data.objects.Category
import com.example.stockapp.databinding.FragmentCreateNewReceitaBinding
import com.example.stockapp.ui.CategoryViewModel
import com.example.stockapp.ui.ReceitasViewModel
import kotlinx.coroutines.launch

class CreateNewReceitaFragment : Fragment() {

    private var _binding: FragmentCreateNewReceitaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewReceitaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel : ReceitasViewModel by activityViewModels()
        val categoryViewModel: CategoryViewModel by activityViewModels()

        binding.inputName.setText("")
        binding.inputPhoto.setText("semfoto")
        binding.inputCalories.setText("0")
        binding.inputIngredientes.setText("")
        binding.inputModoPreparo.setText("")

        val spinner = binding.categorySpinner
        val categoryList = categoryViewModel.categories.value
        val categoryNameList = categoryList?.map { it.name } ?: emptyList()

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            categoryNameList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        var selectedCategory = categoryNameList[0]
        println(selectedCategory)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinner.selectedItem
                if (selectedItem is String) {
                    // You can now access the selected Category object
                    selectedCategory = selectedItem
                    // Do something with selectedCategory
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.btnCreate.setOnClickListener { view ->


            AlertDialog.Builder(view.context)
                .setMessage("Deseja realmente criar esse produto?")
                .setPositiveButton("Confirmar") { dialog, id ->

                    lifecycleScope.launch {
                        try {

                            viewModel.receita.id = 0
                            viewModel.receita.name = binding.inputName.text.toString()
                            viewModel.receita.ingredientes = binding.inputIngredientes.text.toString()
                            viewModel.receita.modo_preparo = binding.inputModoPreparo.text.toString()
                            viewModel.receita.photo = binding.inputPhoto.text.toString()
                            viewModel.receita.calories = binding.inputCalories.text.toString().toInt()
                            viewModel.receita.category_id = categoryViewModel.getByName(selectedCategory).id

                        } catch (e: Exception){

                        }
                        Toast.makeText(context, "Receita criada com sucesso!", Toast.LENGTH_SHORT).show()

                        viewModel.set()

                        binding.inputName.setText("")
                        binding.inputPhoto.setText("semfoto")
                        binding.inputCalories.setText("0")
                        binding.inputIngredientes.setText("")
                        binding.inputModoPreparo.setText("")

                        val action = CreateNewReceitaFragmentDirections.actionCreateNewReceitaFragmentToFragmentHome();
                        findNavController().navigate(action)
                    }

                }
                .setNegativeButton("Cancelar") { dialog, id ->
                    //ignorar
                }.create().show()
            true

        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}