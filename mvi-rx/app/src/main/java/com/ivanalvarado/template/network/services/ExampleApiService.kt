package com.ivanalvarado.template.network.services

import android.database.Observable
import com.ivanalvarado.template.network.ExampleApiResponse
import retrofit2.http.GET

interface ExampleApiService {

    @GET("movie/popular?language=en-US&region=US&page=1")
    fun fetchMoviesByType(): Observable<ExampleApiResponse>
}