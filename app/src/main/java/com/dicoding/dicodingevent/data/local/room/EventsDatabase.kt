package com.dicoding.dicodingevent.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.dicodingevent.data.local.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun eventsDao() : EventsDao

    companion object {
        @Volatile
        private var instance : EventsDatabase? = null
        fun getInstance(context: Context) : EventsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java, "Events.db"
                ).build()
            }
    }
}