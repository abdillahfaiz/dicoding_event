package com.dicoding.dicodingevent.ui.detailEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.data.EventsRepository
import com.dicoding.dicodingevent.data.local.entity.EventEntity
import com.dicoding.dicodingevent.data.remote.response.DetailEvent
import com.dicoding.dicodingevent.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel() : ViewModel(){

    private val _detailEvent = MutableLiveData<DetailEvent>()
    val detailEvent: LiveData<DetailEvent> = _detailEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBar = MutableLiveData<String>()
    val snackBar : LiveData<String> = _snackBar

    companion object {
        private const val TAG = "MainViewModel"
    }



//    fun getFavouritedEvents () = eventsRepository.getFavouritedEvents()
//
//    fun saveEvents (events : EventEntity) = eventsRepository.setFavouritedEvents(events, true)
//
//    fun removeFavouriteEvents (events: EventEntity) = eventsRepository.setFavouritedEvents(events , false)

    fun getDetailEvent(id : String ) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailEvent(id.toInt())

        client.enqueue(object : Callback<DetailEvent> {
            override fun onResponse(call: Call<DetailEvent>, response: Response<DetailEvent>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()
                    _detailEvent.value = result!!
                }
            }

            override fun onFailure(call: Call<DetailEvent>, t: Throwable) {
                _isLoading.value = false
                _snackBar.value = t.message
            }

        })
    }

    fun setFavourite () {
        val eventsRepository = EventsRepository
    }
}