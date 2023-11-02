package com.dicoding.picodiploma.loginwithanimation.view.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    var image: String,
    var name: String,
    var description: String
): Parcelable
