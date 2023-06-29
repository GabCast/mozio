package com.example.mozio.stamps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StampsViewmodel @Inject constructor(private val repository: StampsRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

    val onSuccess = MutableLiveData<Pair<Array<String>, Array<String>>>()

    fun calculate(firstPersonStamps: String, secondPersonStamps: String) {
        viewModelScope.launch(Dispatchers.Main) {
            isLoading.postValue(true)
            val response = repository.calculate(
                convertStampsIntoArrays(firstPersonStamps),
                convertStampsIntoArrays(secondPersonStamps)
            )
            onSuccess.postValue(response)
            isLoading.postValue(false)
        }
    }

    private fun convertStampsIntoArrays(stamps: String): Array<String> {
        return stamps.split(",").toTypedArray()
    }

}
