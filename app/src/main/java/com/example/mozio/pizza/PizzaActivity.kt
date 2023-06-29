package com.example.mozio.pizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.mozio.databinding.ActivityPizzaBinding
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

    private fun setProductsAdapter(data: ArrayList<Pizza>) {
        productsAdapter = ProductsAdapter(data, object : ProductsAdapter.ItemClickListener {
            override fun onItemClicked(product: Pizza) {

            }
        })

        with(binding!!) {
            rcvListItems.adapter = productsAdapter
            rcvListItems.setHasFixedSize(true)
        }
    }
}