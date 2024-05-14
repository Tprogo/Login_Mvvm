package com.example.loginmvvmapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.loginmvvmapp.model.UserTwo
import com.example.loginmvvmapp.repositories.DataRepository
import com.example.loginmvvmapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GetDataViewModel @Inject constructor(private val dataRepository: DataRepository):  ViewModel() {

    val getLiveData: LiveData<NetworkResult<UserTwo>>
    get() = dataRepository.liveData

    suspend fun getData(){
        dataRepository.getData()
    }
}