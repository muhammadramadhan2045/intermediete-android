package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X,-30f,30f).apply {
            duration = 6000
            repeatCount=ObjectAnimator.INFINITE
            repeatMode=ObjectAnimator.REVERSE

            val title=ObjectAnimator.ofFloat(binding.titleTextView,View.ALPHA,1f).setDuration(100)
            val name=ObjectAnimator.ofFloat(binding.nameTextView,View.ALPHA,1f).setDuration(100)
            val email=ObjectAnimator.ofFloat(binding.emailTextView,View.ALPHA,1f).setDuration(100)
            val password=ObjectAnimator.ofFloat(binding.passwordTextView,View.ALPHA,1f).setDuration(100)
            val signup=ObjectAnimator.ofFloat(binding.signupButton,View.ALPHA,1f).setDuration(100)
            val nameEdit=ObjectAnimator.ofFloat(binding.nameEditText,View.ALPHA,1f).setDuration(100)
            val emailEdit=ObjectAnimator.ofFloat(binding.emailEditText,View.ALPHA,1f).setDuration(100)
            val passwordEdit=ObjectAnimator.ofFloat(binding.passwordEditText,View.ALPHA,1f).setDuration(100)

            AnimatorSet().apply {
                playSequentially(title,name,email,password,signup,nameEdit,emailEdit,passwordEdit)
                startDelay=100
                start()
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    }
}