package com.example.nikeapp.service

import com.example.nikeapp.model.SearchResultList
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {

  @Headers(
    "x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com",
    "x-rapidapi-key: 09b9bd5170mshb29b9411a919e7ep1787c8jsn73523eb0dc63"
  )
  @GET("define")
  suspend fun fetchSearchResults(@Query("term") term: String): SearchResultList

}