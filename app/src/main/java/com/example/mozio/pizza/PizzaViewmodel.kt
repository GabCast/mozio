package com.example.mozio.pizza

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PizzaViewmodel @Inject constructor(private val repository: PizzaRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val onSuccess = MutableLiveData<ArrayList<Pizza>>()
    val onConnectionError = MutableLiveData<Boolean>()

    val coroutineExceptionHandler = CoroutineExceptionHandler { e, y ->
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
