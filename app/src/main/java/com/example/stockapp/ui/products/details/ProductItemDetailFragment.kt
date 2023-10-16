package com.example.stockapp.ui.products.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.stockapp.databinding.FragmentHomeBinding
import com.example.stockapp.databinding.FragmentProductItemDetailBinding
import com.example.stockapp.ui.home.HomeViewModel
import com.example.stockapp.ui.products.ProductViewModel
import com.example.stockapp.ui.products.ProductsFragmentDirections

class ProductItemDetailFragment : Fragment() {

    private var _binding: FragmentProductItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductItemDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel : ProductViewModel by activityViewModels()


        var product = viewModel.product

        binding.inputDesc.setText(product.description)
        binding.inputPreco.setText(product.price.toString())
        binding.inputQuantity.setText(product.quantity.toString())
        binding.inputName.setText(product.name)
        binding.inputPhoto.setText(product.photo.toString())

        binding.btnSalvar.setOnClickListener { view ->

            AlertDialog.Builder(view.context)
                .setMessage("Deseja realmente editar esse produto?")
                .setPositiveButton("Confirmar") { dialog, id ->

                    try {
                        viewModel.product.name = binding.inputName.text.toString()
                        viewModel.product.description = binding.inputDesc.text.toString()
                        viewModel.product.price = binding.inputPreco.text.toString().toDouble()
                        viewModel.product.photo = binding.inputPhoto.text.toString()
                        viewModel.product.quantity = binding.inputQuantity.text.toString().toInt()


                    } catch (e: Exception){
                    }
                    Toast.makeText(context, "Produto editado com sucesso!", Toast.LENGTH_SHORT).show()
                    viewModel.set()
                    val action = ProductItemDetailFragmentDirections.actionProductItemDetailFragmentToFragmentProducts();
                    findNavController().navigate(action)

                }
                .setNegativeButton("Cancelar") { dialog, id ->
                    //ignorar
                }.create().show()
            true

        }

        binding.btnDelete.setOnClickListener { view ->
            AlertDialog.Builder(view.context)
                .setMessage("Deseja realmente deletar esse produto?")
                .setPositiveButton("Confirmar") { dialog, id ->

                    Toast.makeText(context,  viewModel.product.name + " deletado com sucesso!", Toast.LENGTH_SHORT).show()

                    viewModel.delete(viewModel.product.id)

                    val action = ProductItemDetailFragmentDirections.actionProductItemDetailFragmentToFragmentProducts();
                    findNavController().navigate(action)
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