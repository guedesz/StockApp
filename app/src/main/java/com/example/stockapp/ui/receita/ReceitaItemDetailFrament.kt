package com.example.stockapp.ui.receita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.stockapp.data.Photos
import com.example.stockapp.databinding.FragmentReceitaItemDetailFramentBinding
import com.example.stockapp.ui.ReceitasViewModel


class ReceitaItemDetailFrament : Fragment() {

    private var _binding: FragmentReceitaItemDetailFramentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceitaItemDetailFramentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel : ReceitasViewModel by activityViewModels()
        var receita = viewModel.receita

        binding.ingredientesView.setText(receita.ingredientes)
        binding.receitaNome.text = receita.name
        binding.modoView.text = receita.modo_preparo
        val imgId = Photos.get(receita.photo)
        binding.receitaFoto.setImageResource(imgId)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}