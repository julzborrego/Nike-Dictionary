package com.example.nikeapp

import android.app.Application
import com.example.nikeapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NikeApp : Application(){
  override fun onCreate() {
    super.onCreate()

    //Instantiate Koin
    startKoin {
      androidContext(this@NikeApp)
      modules(appModule)
    }
  }
}