package com.dicoding.dicodingevent.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.dicoding.dicodingevent.data.local.entity.EventEntity

@Dao
interface EventsDao {

    @Query("SELECT * FROM events WHERE favourite = 1")
    fun getFavouritedEvents() : LiveData<List<EventEntity>>

    @Query("SELECT EXISTS(SELECT * FROM events WHERE name = :name AND favourite = 1)")
    fun isEventFavourite(name : String): Boolean

    @Query("DELETE FROM events WHERE favourite = 1")
    fun deleteAll()

    @Update
    fun updateEvents(events : EventEntity)

}