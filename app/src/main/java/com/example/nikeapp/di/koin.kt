package com.example.nikeapp.di

import com.example.nikeapp.repo.NikeRepository
import com.example.nikeapp.repo.Repository
import com.example.nikeapp.service.SearchService
import com.example.nikeapp.utils.baseUrl
import com.example.nikeapp.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//Handle Dependency Injection
val appModule = module {
  single {
    Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(MoshiConverterFactory.create())
      .build().create(SearchService::class.java)
  }

  single<Repository> {
    NikeRepository(get())
  }

  viewModel { MainViewModel(get(), Dispatchers.IO) }
}