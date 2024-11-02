package com.dicoding.dicodingevent.data.remote.retrofit

import com.dicoding.dicodingevent.data.remote.response.DetailEvent
import com.dicoding.dicodingevent.data.remote.response.ListOfEvent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/events/{id}")
    fun getDetailEvent(
        @Path("id") id : Int
    ) : Call<DetailEvent>

    @GET("/events")
    fun getListEvent(
        @Query("active") active : Int
    ) : Call<ListOfEvent>


}