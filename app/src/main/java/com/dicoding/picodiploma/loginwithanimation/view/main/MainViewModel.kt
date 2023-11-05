package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.response.StoriesResponse
import com.dicoding.picodiploma.loginwithanimation.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    val listStories: LiveData<List<ListStoryItem>> = repository.listStories
    val snackbarText: LiveData<Event<String>> = repository.snackbarText
    val isLoading: LiveData<Boolean> = repository.isLoading
    val user = repository.user
    val getStories: LiveData<PagingData<ListStoryItem>> = repository.getStories().cachedIn(viewModelScope)

//    fun getStories(token: String) {
//        viewModelScope.launch {
//            repository.getStories(token)
//        }
//    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }


    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


}