package com.example.mozio.pizza

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mozio.databinding.ItemProductBinding

class ProductsAdapter(
    private val products: List<Pizza>,
    private val itemClickListener: ItemClickListener
) :
    Adapter<ProductsAdapter.ProductsViewHolder>() {

    interface ItemClickListener {
        fun onItemClicked(product: Pizza)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemProductBinding.inflate(
                from(
                    parent.context
                ), parent, false
            ), itemClickListener
        )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(products[position])
    }

    class ProductsViewHolder(
        private val binding: ItemProductBinding,
        private val itemClickListener: ItemClickListener
    ) :
        ViewHolder(binding.root) {

        fun bind(content: Pizza) {
            with(binding) {
                productName.text = content.name
                productPrice.text = content.price.toString()
                root.setOnClickListener {
                    itemClickListener.onItemClicked(content)
                }
            }
        }
    }
}
