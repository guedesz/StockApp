package com.example.stockapp.ui.categories

import com.example.stockapp.ui.CategoryViewModel
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import coil.load
import com.example.stockapp.data.Colors
import com.example.stockapp.data.Photos
import com.example.stockapp.data.objects.Category
import com.example.stockapp.databinding.FragmentCategoryItemBinding
import com.example.stockapp.ui.home.HomeFragmentDirections
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

class CategoryAdapter(
    private val categories: List<Category>,
    val viewModel: CategoryViewModel
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CategoryViewHolder {

        return CategoryViewHolder(
            FragmentCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

//        val imgId = Photos.get(category.photo)

        Firebase.storage.getReference(category.photo).downloadUrl
            .addOnSuccessListener { imageUrl ->
                holder.imgPhoto.load(imageUrl)
            }
            .addOnFailureListener { exception ->
                // Tratamento de erro ao obter a URL da imagem
                // Aqui você pode lidar com o erro, seja registrando, notificando o usuário, etc.
                println("Erro ao obter URL da imagem: $exception")
            }

        // holder.imgPhoto.setImageResource(imgId)
        holder.txtName.text = category.name
        holder.binding.root.setBackgroundColor((Color.parseColor(Colors.get(category.color))))

        holder.itemView.setOnClickListener {view ->
            val action = HomeFragmentDirections.actionFragmentHomeToReceitasFragment(category.name)
            action.categoryName = category.name
            view.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(binding: FragmentCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgPhoto: ImageView = binding.imageView
        val txtName: TextView = binding.categoryName
        val binding = binding
    }

}
