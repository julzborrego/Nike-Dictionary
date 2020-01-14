package com.example.nikeapp.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.nikeapp.repo.Repository
import com.example.nikeapp.model.Result
import kotlinx.coroutines.CoroutineDispatcher

class MainViewModel(private val repo: Repository, ioDispatcher: CoroutineDispatcher) : ViewModel() {

  private val searchInput = MutableLiveData<String>()

  fun setSearchInput(input:String){
    searchInput.value = input
  }

  /**
   * Transforms the search input mutable live data to a live data of the search result.
   */
  val searchResults = Transformations.switchMap(searchInput) { input ->
    liveData(ioDispatcher) {
      emit(Result.Loading)
      try {
        val result = repo.fetchSearchResults(input)
        emit(Result.Success(result))
      } catch (e: Exception) {
        //Handle any errors fetching the data
        emit(Result.Error(e))
      }
    }
  }
}