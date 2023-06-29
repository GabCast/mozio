package com.example.mozio.stamps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.mozio.R
import com.example.mozio.databinding.ActivityMainBinding
import com.example.mozio.databinding.ActivityStampsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StampsActivity : AppCompatActivity() {
    private var binding: ActivityStampsBinding? = null
    private val viewModel: StampsViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStampsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        calculateStamps()
        setObservers()
    }

    private fun calculateStamps() {
        binding?.buttonCalculateStamps?.setOnClickListener {
            if (validateInputs()) {
                viewModel.calculate(binding?.inputFirstPerson?.editText?.text.toString(),
                    binding?.inputSecondPerson?.editText?.text.toString())
            } else {
                Toast.makeText(this, "Add the stamps", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs() : Boolean {
        return !(binding?.inputFirstPerson?.editText?.text.isNullOrEmpty() ||
                binding?.inputSecondPerson?.editText?.text.isNullOrEmpty())
    }

    private fun setObservers() {
        viewModel.isLoading.observe(this) {
            binding?.progressBar?.isVisible = it
        }
        viewModel.onSuccess.observe(this) {
            binding?.firstPersonGets?.text = it.first.contentToString()
            binding?.secondPersonGets?.text = it.second.contentToString()
        }
    }
}