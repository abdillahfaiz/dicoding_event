package com.dicoding.dicodingevent.ui.finishedEvent

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.data.remote.response.ListOfEvent
import com.dicoding.dicodingevent.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedEventViewModel : ViewModel() {

    private val _listEvents = MutableLiveData<List<ListEventsItem>>()
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBar = MutableLiveData<String>()
    val snackBar : LiveData<String> = _snackBar

    companion object {
        private const val TAG = "MainViewModel"
        private const val ACTIVE = 0
    }

    init {
        getListEvents()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getListEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListEvent(ACTIVE)

            client.enqueue(object : Callback<ListOfEvent> {
                override fun onResponse(call: Call<ListOfEvent>, response: Response<ListOfEvent>) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        val result = response.body()

                        _listEvents.value = result!!.listEvents

                    }
                }

                override fun onFailure(call: Call<ListOfEvent>, t: Throwable) {
                    _isLoading.value = false
                    _snackBar.value = t.message
                }

            })

    }

}