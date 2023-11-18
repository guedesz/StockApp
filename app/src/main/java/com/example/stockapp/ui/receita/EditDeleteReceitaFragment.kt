import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stockapp.databinding.FragmentCreateNewReceitaBinding
import com.example.stockapp.databinding.FragmentEditDeleteReceitaBinding
import com.example.stockapp.ui.CategoryViewModel
import com.example.stockapp.ui.ReceitasViewModel
import com.example.stockapp.ui.receita.CreateNewReceitaFragmentDirections
import kotlinx.coroutines.launch

class EditDeleteReceitaFragment : Fragment() {

    private var _binding: FragmentEditDeleteReceitaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditDeleteReceitaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel : ReceitasViewModel by activityViewModels()
        val categoryViewModel: CategoryViewModel by activityViewModels()

        val receita = viewModel.receita

        binding.nameInput.setText(receita.name)
        binding.PhotoInput.setText(receita.photo)
        binding.CaloriesInput.setText(receita.calories.toString())
        binding.IngredientsInput.setText(receita.ingredientes)
        binding.ModoPreparoInput.setText(receita.modo_preparo)

        val spinner = binding.categorySpinner
        val categoryList = categoryViewModel.categories.value

        val categoryNameList = categoryList?.map { it.name } ?: emptyList()

        if (categoryNameList.isEmpty()) {
            return root
        }

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner_item,
            categoryNameList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        lifecycleScope.launch {

            var selectedCategory = categoryViewModel.getById(receita.category_id).name

            val position = categoryNameList.indexOf(selectedCategory)
            if (position != -1) {
                spinner.setSelection(position)
            }

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


            binding.btnExcluir.setOnClickListener { view ->
                AlertDialog.Builder(view.context)
                    .setMessage("Deseja realmente deletar essa receita?")
                    .setPositiveButton("Confirmar") { dialog, id ->

                        Toast.makeText(context,  viewModel.receita.name + " deletado com sucesso!", Toast.LENGTH_SHORT).show()

                        viewModel.delete(viewModel.receita)

                        val action = EditDeleteReceitaFragmentDirections.actionEditDeleteReceitaFragmentToFragmentHome();
                        findNavController().navigate(action)
                    }
                    .setNegativeButton("Cancelar") { dialog, id ->
                        //ignorar
                    }.create().show()
                true
            }


            binding.btnEdit.setOnClickListener { view ->

                AlertDialog.Builder(view.context)
                    .setMessage("Deseja realmente editar esse produto?")
                    .setPositiveButton("Confirmar") { dialog, id ->

                        lifecycleScope.launch {
                            try {

                                viewModel.receita.name = binding.nameInput.text.toString()
                                viewModel.receita.ingredientes = binding.IngredientsInput.text.toString()
                                viewModel.receita.modo_preparo = binding.ModoPreparoInput.text.toString()
                                viewModel.receita.photo = binding.PhotoInput.text.toString()
                                viewModel.receita.calories = binding.CaloriesInput.text.toString().toInt()
                                viewModel.receita.category_id = categoryViewModel.getByName(selectedCategory).id

                            } catch (e: Exception){

                            }
                            Toast.makeText(context, "Receita editada com sucesso!", Toast.LENGTH_SHORT).show()

                            viewModel.set()

                            val action = EditDeleteReceitaFragmentDirections.actionEditDeleteReceitaFragmentToFragmentHome();
                            findNavController().navigate(action)
                        }
                    }

                    .setNegativeButton("Cancelar") { dialog, id ->
                        //ignorar
                    }.create().show()
                true

            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}