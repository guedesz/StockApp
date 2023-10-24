package com.example.stockapp.ui.categories

import CategoryViewModel
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.stockapp.data.Photos
import com.example.stockapp.data.objects.Category
import com.example.stockapp.databinding.FragmentCategoryItemBinding

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

        val imgId = Photos.get(category.photo)
        holder.imgPhoto.setImageResource(imgId)
        holder.txtName.text = category.name
        holder.binding.root.setBackgroundColor((Color.parseColor(category.color)))

//        holder.editButton.setOnClickListener { view ->
//            viewModel.edit(product)
//            val action = ProductsFragmentDirections.actionFragmentProductsToProductItemDetailFragment()
//            view.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(binding: FragmentCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgPhoto: ImageView = binding.imageView
        val txtName: TextView = binding.categoryName
        val binding = binding
    }

}
