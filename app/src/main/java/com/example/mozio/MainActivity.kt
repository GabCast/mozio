package com.example.mozio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mozio.databinding.ActivityMainBinding
import com.example.mozio.pizza.PizzaActivity
import com.example.mozio.stamps.StampsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.goToStamps?.setOnClickListener {
            startActivity(Intent(this, StampsActivity::class.java))
        }

        binding?.goToPizza?.setOnClickListener {
            startActivity(Intent(this, PizzaActivity::class.java))
        }
    }
}