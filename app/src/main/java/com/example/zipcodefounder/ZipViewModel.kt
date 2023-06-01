package com.example.zipcodefounder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zipcodefounder.api.ZipCodeApi
import com.example.zipcodefounder.model.DetailsDTO
import com.example.zipcodefounder.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import kotlinx.coroutines.launch

class ZipViewModel : ViewModel() {
    sealed class State {
        data class Success(val region: DetailsDTO) : State()
        object Failure : State()
        object Loading : State()
        object Empty : State()
    }

    private val state: MutableLiveData<State> = MutableLiveData(State.Empty)
    private val zipApiService: ZipCodeApi = RetrofitClient.zipApi

    fun observeState(): LiveData<State> = state

    fun searchZip(zipCode: String) {
        this.viewModelScope.launch {
            state.value = State.Loading
            try {
                val response = zipApiService.getRegionByZip(zipCode)
                if (response.isSuccessful) {
                    val region = response.body()
                    region?.let {
                        state.value = State.Success(it)
                    } ?: run {
                        state.value = State.Failure
                    }
                } else {
                    state.value = State.Failure
                }
            } catch (e: Throwable) {
                state.value = State.Failure
            }
        }
    }
}
