package com.example.nikeapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nikeapp.main.MainViewModel
import com.example.nikeapp.model.Result
import com.example.nikeapp.model.SearchResultList
import com.example.nikeapp.repo.Repository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


@ExperimentalCoroutinesApi
class MainViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  lateinit var mainViewModel: MainViewModel

  val repositoryMock = mock<Repository>()

  @Before
  fun setup() {
    mainViewModel =
      MainViewModel(repositoryMock, mainCoroutineRule.testDispatcher)
  }

  @Test
  fun testFetchSearchResults() = mainCoroutineRule.runBlockingTest {
    //Given
    val term = "search"
    val searchResultList = SearchResultList(emptyList())
    doReturn(searchResultList).`when`(repositoryMock).fetchSearchResults(term)

    //When
    mainViewModel.setSearchInput(term)
    val result = mainViewModel.searchResults
    result.observeForTesting {
      //Then
      assertEquals(searchResultList, (result.value as Result.Success).data)
    }

  }

  @Test
  fun testFetchSearchResultsError() = mainCoroutineRule.runBlockingTest {
    //Given
    val term = "search"
    val exception = RuntimeException()
    doThrow(exception).`when`(repositoryMock).fetchSearchResults(term)
    val result = mainViewModel.searchResults

    //When
    mainViewModel.setSearchInput(term)
    result.observeForTesting {
      //Then
      assertEquals(exception, (result.value as Result.Error).exception)
    }
  }
}