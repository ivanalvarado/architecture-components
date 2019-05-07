package com.ivanalvarado.architecture_components.network.services

import android.database.Observable
import com.ivanalvarado.architecture_components.network.ExampleApiResponse
import retrofit2.http.GET

interface ExampleApiService {

    @GET("movie/popular?language=en-US&region=US&page=1")
    fun fetchMoviesByType(): Observable<ExampleApiResponse>
}