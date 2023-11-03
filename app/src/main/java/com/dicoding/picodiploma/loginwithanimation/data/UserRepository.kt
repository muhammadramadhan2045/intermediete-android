package com.dicoding.picodiploma.loginwithanimation.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.AddStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.ResultState
import com.dicoding.picodiploma.loginwithanimation.data.response.StoriesResponse
import com.dicoding.picodiploma.loginwithanimation.utils.Event
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.net.UnknownHostException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _listStories=MutableLiveData<List<ListStoryItem>>()
    val listStories:LiveData<List<ListStoryItem>> = _listStories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    init {
        getStories()
    }
    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = apiService.register(name, email, password)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                try {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _registerResponse.value = response.body()
                        _snackbarText.value = Event(response.body()?.message.toString())
                        Log.e("Register Success", "${response.body()?.message}")
                    } else {
                        //akses json object
                        val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                        val error = jsonObject?.getBoolean("error")
                        val message = jsonObject?.getString("message")
                        _registerResponse.value = RegisterResponse(error, message)
                        _snackbarText.value = Event(
                            "$message"
                        )

                        Log.e("Register Failure bg", "$message")
                    }
                } catch (e: JSONException) {
                    _snackbarText.value = Event(e.message.toString())
                    Log.e("Register Failure", "${e.message}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _snackbarText.value = Event("No Internet Connection")
                        Log.e("UnknownHostException", "${t.message}")
                    }

                    else -> {
                        _snackbarText.value = Event(t.message.toString())
                        Log.e("Unknown Error", "${t.message}")
                    }
                }
            }
        })

    }


    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.login(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                try {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _loginResponse.value = response.body()
                        _snackbarText.value = Event(response.body()?.message.toString())
                        Log.e("Login Success", "${response.body()?.message}")
                    } else {
                        //akses json object
                        val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                        val error = jsonObject?.getBoolean("error")
                        val message = jsonObject?.getString("message")
                        _loginResponse.value = LoginResponse(null, error, message)
                        _snackbarText.value = Event(
                            "$message"
                        )

                        Log.e("Login Failure bg", "$message")
                    }
                } catch (e: JSONException) {
                    _snackbarText.value = Event(e.message.toString())
                    Log.e("Login Failure", "${e.message}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _snackbarText.value = Event("No Internet Connection")
                        Log.e("UnknownHostException", "${t.message}")
                    }

                    else -> {
                        _snackbarText.value = Event(t.message.toString())
                        Log.e("Unknown Error", "${t.message}")
                    }
                }
            }
        })
    }

    fun getStories() {
        _isLoading.value = true
        val client = apiService.getStories()
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                try {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody=response.body()
                        _listStories.value =responseBody?.listStory
                        _snackbarText.value = Event(response.body()?.message.toString())
                        Log.e("Stories Success", "${response.body()?.message}")
                    }
                } catch (e: JSONException) {
                    _snackbarText.value = Event(e.message.toString())
                    Log.e("Stories Failure", "${e.message}")
                }

            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _snackbarText.value = Event("No Internet Connection")
                        Log.e("UnknownHostException", "${t.message}")
                    }

                    else -> {
                        _snackbarText.value = Event(t.message.toString())
                        Log.e("Unknown Error", "${t.message}")
                    }
                }
            }
        })
    }



    fun addStory(imageFile: File, description: String) = liveData<Any> {
        emit(ResultState.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.addStory(multipartBody, requestBody)
            emit(ResultState.Success(successResponse.message))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddStoryResponse::class.java)
            emit(ResultState.Error(errorResponse.message.toString()))
        }

    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun accountLogin() {
        userPreference.accountLogin()
    }

    suspend fun logout() {
        userPreference.logout()
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference, apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}