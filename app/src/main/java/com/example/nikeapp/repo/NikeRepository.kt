package com.example.nikeapp.repo

import com.example.nikeapp.service.SearchService

class NikeRepository(private val employeeService: SearchService) : Repository {

  override suspend fun fetchSearchResults(term: String) = employeeService.fetchSearchResults(term)

}