package com.example.mozio.pizza.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.mozio.databinding.ActivityPizzaBinding
import com.example.mozio.pizza.model.Menu
import com.example.mozio.pizza.model.Order
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PizzaActivity : AppCompatActivity() {

    private var binding: ActivityPizzaBinding? = null
    private val viewModel: PizzaViewmodel by viewModels()
    private var productsAdapter: ProductsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPizzaBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setObservers()
        viewModel.menu()

        binding?.buyOrder?.setOnClickListener {
            val args = Bundle().apply {
                putParcelable(ORDER, viewModel.order.value)
            }
            startActivity(
                Intent(this, CheckoutActivity::class.java).putExtra(
                    ORDER_BUNDLE,
                    args
                )
            )
        }
    }

    private fun setObservers() {
        viewModel.isLoading.observe(this) {
            binding?.progressBar?.isVisible = it
        }
        viewModel.onSuccess.observe(this) { menu ->
            if (menu.isNotEmpty()) {
                setProductsAdapter(menu)
            } else {
                Toast.makeText(this, "There is no menu", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.onConnectionError.observe(this) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setProductsAdapter(data: ArrayList<Menu>) {
        productsAdapter = ProductsAdapter(object : ProductsAdapter.ItemClickListener {
            override fun onHalfClicked(product: Menu) {
                when {
                    viewModel.order.value?.flavors.isNullOrEmpty() -> {
                        viewModel.order.value = Order(product.price / 2, arrayListOf(product.name))
                        viewModel.completed.value = false
                        productsAdapter?.products = data.onEach { it.totalDisabled = true }
                        productsAdapter?.notifyDataSetChanged()
                    }

                    viewModel.order.value?.flavors?.size == 1 -> {
                        viewModel.order.value?.price =
                            viewModel.order.value?.price!! + product.price / 2
                        viewModel.order.value?.flavors!!.add(product.name)
                        viewModel.completed.value = true
                    }

                    else -> {
                        Toast.makeText(
                            this@PizzaActivity,
                            "The order is completed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                binding?.buyOrder?.isEnabled = viewModel.completed.value!!
            }

            override fun onTotalClicked(product: Menu) {
                if (viewModel.completed.value == true) {
                    Toast.makeText(
                        this@PizzaActivity,
                        "The order is completed",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.order.value = Order(product.price, arrayListOf(product.name))
                    viewModel.completed.value = true
                }
                binding?.buyOrder?.isEnabled = viewModel.completed.value!!
            }
        }).apply {
            products = data
        }

        with(binding!!) {
            rcvListItems.adapter = productsAdapter
            rcvListItems.setHasFixedSize(true)
        }
    }

    companion object {
        const val ORDER = "order"
        const val ORDER_BUNDLE = "order_bundle"
    }
}