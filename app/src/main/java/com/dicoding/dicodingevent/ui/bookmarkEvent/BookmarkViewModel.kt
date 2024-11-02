package com.dicoding.dicodingevent.ui.bookmarkEvent

import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.data.EventsRepository
import com.dicoding.dicodingevent.data.local.entity.EventEntity

class BookmarkViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    fun getFavouritedEvents () = eventsRepository.getFavouritedEvents()

    fun saveEvents (events : EventEntity) = eventsRepository.setFavouritedEvents(events, true)

    fun removeFavouriteEvents (events: EventEntity ) = eventsRepository.setFavouritedEvents(events , false)

}