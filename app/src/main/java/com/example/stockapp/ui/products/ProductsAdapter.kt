package br.edu.up.app.ui.produto

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.stockapp.data.Product
import com.example.stockapp.databinding.FragmentProductItemBinding
import com.example.stockapp.ui.products.ProductViewModel

class ProductsAdapter(
    private val products: List<Product>,
    val viewModel: ProductViewModel
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductViewHolder {

        return ProductViewHolder(
            FragmentProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
//
//        val imgId = Photos.get(product.photo)
//        holder.imgPhoto.setImageResource(imgId)
//        holder.txtName.text = product.name
//        holder.txtQuantity.text = "Quantity Left: " + product.quantity.toString()
//
//        holder.editButton.setOnClickListener { view ->
//            viewModel.edit(product)
//            val action = ProductsFragmentDirections.actionFragmentProductsToProductItemDetailFragment()
//            view.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(binding: FragmentProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgPhoto: ImageView = binding.imgPhoto
        val txtQuantity: TextView = binding.txtStock
        val txtName: TextView = binding.txtName
        val editButton: Button = binding.editButton
    }

}
