package com.dicoding.picodiploma.loginwithanimation.view.customize

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.loginwithanimation.R
import java.util.regex.Pattern

class MyEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private lateinit var clearButtonImage: Drawable

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (inputType == TYPE_TEXT_VARIATION_EMAIL_ADDRESS+1) {
                    if (!isValidEmail(s.toString())) {
                        error = "Email tidak valid"
                    } else {
                        error = null
                    }
                } else if (inputType == TYPE_TEXT_VARIATION_PASSWORD+1) {
                    if (s.toString().length < 8) {
                        error = "Password tidak boleh kurang dari 8 karakter"
                    } else {
                        error = null
                    }
                } else {
                    // Tidak ada validasi khusus untuk jenis input lainnya
                    error = null
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val pattern = Pattern.compile(emailPattern)
        return pattern.matcher(email).matches()
    }


}
