package com.example.mozio.pizza.ui

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mozio.databinding.ItemProductBinding
import com.example.mozio.pizza.model.Menu

class ProductsAdapter(
    private val itemClickListener: ItemClickListener
) :
    Adapter<ProductsAdapter.ProductsViewHolder>() {

    var products: MutableList<Menu> = mutableListOf()

    interface ItemClickListener {
        fun onHalfClicked(product: Menu)
        fun onTotalClicked(product: Menu)
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

        fun bind(content: Menu) {
            with(binding) {
                total.isEnabled = !content.totalDisabled

                productName.text = content.name
                productPrice.text = content.price.toString()
                half.setOnClickListener {
                    itemClickListener.onHalfClicked(content)
                }
                total.setOnClickListener {
                    itemClickListener.onTotalClicked(content)
                }
            }
        }
    }
}
