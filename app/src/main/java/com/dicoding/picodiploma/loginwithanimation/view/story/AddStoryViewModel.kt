package com.dicoding.picodiploma.loginwithanimation.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.utils.Event
import java.io.File

class AddStoryViewModel(private val repository: UserRepository) : ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading
    val snackbarText: LiveData<Event<String>> = repository.snackbarText
    fun addStory(token:String,file: File, description: String) = repository.addStory(token,file, description)
    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }
}