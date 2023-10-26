package com.example.stockapp.ui.receita

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.stockapp.data.Photos
import com.example.stockapp.data.objects.Receita
import com.example.stockapp.databinding.FragmentReceitaItemBinding
import com.example.stockapp.ui.ReceitasViewModel
import com.example.stockapp.ui.categories.CategoryAdapter
import com.example.stockapp.ui.home.HomeFragmentDirections

class ReceitasAdapter(
    private val receitas: List<Receita>,
    val viewModel: ReceitasViewModel
) : RecyclerView.Adapter<ReceitasAdapter.ReceitasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceitasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentReceitaItemBinding.inflate(inflater, parent, false)

        return ReceitasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceitasViewHolder, position: Int) {
        val receita = receitas[position]

        val imgId = Photos.get(receita.photo)
        holder.imgPhoto.setImageResource(imgId)
        holder.txtName.text = receita.name
        holder.txtCalories.text = receita.calories.toString()

        holder.itemView.setOnClickListener {view ->
            viewModel.receita = receita
            val action = ReceitasFragmentDirections.actionReceitasFragmentToReceitaItemDetailFrament()
            view.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int = receitas.size

    inner class ReceitasViewHolder(binding: FragmentReceitaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgPhoto: ImageView = binding.receitaPhoto
        val txtName: TextView = binding.receitaName
        val txtCalories: TextView = binding.caloriesText
        val binding = binding
    }

}
