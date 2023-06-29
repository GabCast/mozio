package com.example.mozio.pizza.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mozio.pizza.model.Menu
import com.example.mozio.pizza.model.Order
import com.example.mozio.pizza.repository.PizzaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PizzaViewmodel @Inject constructor(private val repository: PizzaRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val onSuccess = MutableLiveData<ArrayList<Menu>>()
    val onConnectionError = MutableLiveData<Boolean>()
    val order = MutableLiveData<Order>()
    val completed = MutableLiveData<Boolean>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { e, y ->
        onConnectionError.postValue(true)
    }

    fun menu() {
        viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            isLoading.postValue(true)
            onSuccess.postValue(repository.menu())
            isLoading.postValue(false)
        }
    }

}
