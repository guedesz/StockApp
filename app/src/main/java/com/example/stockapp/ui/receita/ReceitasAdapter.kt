package com.example.stockapp.ui.receita

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.stockapp.data.Photos
import com.example.stockapp.data.objects.Receita
import com.example.stockapp.databinding.FragmentReceitaItemBinding
import com.example.stockapp.ui.ReceitasViewModel
import com.example.stockapp.ui.categories.CategoryAdapter

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
        val category = receitas[position]

        val imgId = Photos.get(category.photo)
//        holder.imgPhoto.setImageResource(imgId)
//        holder.txtName.text = category.name
//        holder.binding.root.setBackgroundColor((Color.parseColor(Colors.get(category.color))))
//
//        holder.itemView.setOnClickListener {view ->
//            val action = HomeFragmentDirections.actionFragmentHomeToReceitasFragment(category.name)
//            action.categoryName = category.name
//            view.findNavController().navigate(action)
//        }

    }

    override fun getItemCount(): Int = receitas.size

    inner class ReceitasViewHolder(binding: FragmentReceitaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        val imgPhoto: ImageView = binding.imageView
//        val txtName: TextView = binding.categoryName
        val binding = binding
    }

}
