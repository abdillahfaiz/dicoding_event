package com.dicoding.dicodingevent.data

import android.provider.CalendarContract.EventsEntity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.dicodingevent.data.local.entity.EventEntity
import com.dicoding.dicodingevent.data.local.room.EventsDao
import com.dicoding.dicodingevent.data.remote.retrofit.ApiService
import com.dicoding.dicodingevent.utils.AppExecutor

class EventsRepository private constructor(
    private val apiService: ApiService,
    private val eventsDao : EventsDao,
    private val appExecutor : AppExecutor
) {
    private val result  =MediatorLiveData<Result<List<EventsEntity>>>()

    fun getFavouritedEvents () : LiveData<List<EventEntity>> {
        return eventsDao.getFavouritedEvents()
    }

    fun setFavouritedEvents (events : EventEntity, favouriteState : Boolean) {
        appExecutor.diskIo.execute {
            events.isFavourite = favouriteState
            eventsDao.updateEvents(events)
        }
    }



    companion object {
        @Volatile
        private var instance: EventsRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventsDao: EventsDao,
            appExecutors: AppExecutor
        ): EventsRepository =
            instance ?: synchronized(this) {
                instance ?: EventsRepository(apiService, eventsDao, appExecutors)
            }.also { instance = it }
    }
}