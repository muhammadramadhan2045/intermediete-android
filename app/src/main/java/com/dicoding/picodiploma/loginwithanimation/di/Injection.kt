package com.dicoding.picodiploma.loginwithanimation.di

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user= runBlocking { pref.getSession().first() }
        val apiService=ApiConfig.getApiService()
        return UserRepository.getInstance(pref,apiService)
    }
}