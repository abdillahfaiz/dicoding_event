package com.dicoding.dicodingevent.ui.UpcomingEvent

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.data.EventsRepository
import com.dicoding.dicodingevent.data.local.entity.EventEntity
import com.dicoding.dicodingevent.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.data.remote.response.ListOfEvent
import com.dicoding.dicodingevent.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    private val _listEvents = MutableLiveData<List<ListEventsItem>>()
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBar = MutableLiveData<String>()
    val snackBar : LiveData<String> = _snackBar

    companion object {
        private const val TAG = "MainViewModel"
        private const val ACTIVE = 1
    }

    init {
        getListEvents()
    }

    fun getFavouritedEvents () = eventsRepository.getFavouritedEvents()

    fun saveEvents (events : EventEntity) = eventsRepository.setFavouritedEvents(events, true)

    fun removeFavouriteEvents (events: EventEntity) = eventsRepository.setFavouritedEvents(events , false)

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