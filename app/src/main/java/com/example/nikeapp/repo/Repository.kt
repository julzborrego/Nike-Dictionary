package com.example.nikeapp.repo

import com.example.nikeapp.model.SearchResultList

interface Repository{
  suspend fun fetchSearchResults(term : String) : SearchResultList
}