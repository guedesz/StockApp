package br.edu.up.app.ui.produto

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.stockapp.data.Photos
import com.example.stockapp.data.Product
import com.example.stockapp.databinding.FragmentProductItemBinding
import com.example.stockapp.ui.products.ProductItemFragment
import com.example.stockapp.ui.products.ProductViewModel
import com.example.stockapp.ui.products.ProductsFragmentDirections
import java.text.DecimalFormat
import java.util.Locale

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

        val imgId = Photos.get(product.photo)
        holder.imgPhoto.setImageResource(imgId)
        holder.txtName.text = product.name
        holder.txtQuantity.text = "Quantity Left: " + product.quantity.toString()

        holder.editButton.setOnClickListener { view ->
            viewModel.edit(product)
            val action = ProductsFragmentDirections.actionFragmentProductsToProductItemDetailFragment()
            view.findNavController().navigate(action)
        }

//        holder.itemView.setOnLongClickListener { view ->
//            AlertDialog.Builder(view.context)
//                .setMessage("ATENÇÃO")
//                .setPositiveButton("Confirmar") { dialog, id ->
//                    viewModel.delete(id)
//                }
//                .setNegativeButton("CANCELAR") { dialog, id ->
//                    //ignorar
//                }.create().show()
//            true
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
