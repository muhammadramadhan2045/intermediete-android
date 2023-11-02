package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

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
            val name= binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignupActivity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if(email.isNotEmpty() || name.isNotEmpty() || password.isNotEmpty()){
                showLoading()
                postRegister(name, email, password)
                showSnackBar()
                toLogin()
            }

        }
    }

    private fun showLoading(){
        viewModel.isLoading.observe(this@SignupActivity){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showSnackBar(){
        viewModel.snackbarText.observe(this@SignupActivity){
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this@SignupActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toLogin(){
        viewModel.registerResponse.observe(this@SignupActivity){
            if(it.error == false){
                val intent = Intent(this@SignupActivity,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }


    private fun postRegister(name:String, email:String, password:String){
        viewModel.register(name, email, password)
    }
}