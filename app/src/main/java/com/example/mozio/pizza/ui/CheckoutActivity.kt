package com.example.mozio.pizza.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mozio.databinding.ActivityCheckoutBinding
import com.example.mozio.pizza.model.Order
import com.example.mozio.pizza.ui.PizzaActivity.Companion.ORDER
import com.example.mozio.pizza.ui.PizzaActivity.Companion.ORDER_BUNDLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    private var binding: ActivityCheckoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val order: Order = intent.extras?.getBundle(ORDER_BUNDLE)?.getParcelable(ORDER)!!

        with(order) {
            binding?.orderFlavors?.text = flavors.toString()
            binding?.orderPrice?.text = price.toString()
        }
    }
}