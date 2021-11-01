package com.monika.kisshttask.network

import com.monika.kisshttask.model.Poster
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("?client_id=nXeeH3UgNgOEUC__2ZQ31h3N94eOHgGsgYnzA1muDIc")
    suspend fun fetchList(): ApiResponse<List<Poster>>
}
