package com.example.stockapp.ui.products.create

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.stockapp.data.Product
import com.example.stockapp.databinding.FragmentCreateProductBinding
import com.example.stockapp.ui.products.ProductViewModel

class CreateProductFragment : Fragment() {

    private var _binding: FragmentCreateProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel : ProductViewModel by activityViewModels()

        binding.inputDesc.setText("")
        binding.inputPreco.setText("0")
        binding.inputQuantity.setText("0")
        binding.inputName.setText("")
        binding.inputPhoto.setText("")

        binding.btnCreate.setOnClickListener { view ->


            AlertDialog.Builder(view.context)
                .setMessage("Deseja realmente criar esse produto?")
                .setPositiveButton("Confirmar") { dialog, id ->


                    try {

                        viewModel.product.name = binding.inputName.text.toString()
                        viewModel.product.description = binding.inputDesc.text.toString()
                        viewModel.product.price = binding.inputPreco.text.toString().toDouble()
                        viewModel.product.photo = binding.inputPhoto.text.toString()
                        var quantity = binding.inputQuantity.text.toString()
                        viewModel.product.quantity = quantity.toInt()

                    } catch (e: Exception){

                    }
                    Toast.makeText(context, "Produto criado com sucesso!", Toast.LENGTH_SHORT).show()

                    viewModel.set()


//                    val action = CreateProductFragmentDirections.actionFragmentCreateProductToFragmentProducts();
//                    findNavController().navigate(action)
                    binding.inputDesc.setText("")
                    binding.inputPreco.setText("0")
                    binding.inputQuantity.setText("0")
                    binding.inputName.setText("")
                    binding.inputPhoto.setText("")

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