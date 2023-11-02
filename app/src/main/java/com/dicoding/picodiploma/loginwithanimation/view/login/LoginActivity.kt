package com.dicoding.picodiploma.loginwithanimation.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)




        setupView()
        setupAction()

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
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password=binding.passwordEditText.text.toString()

            if(email.isEmpty()&&password.isEmpty()){
                Toast.makeText(this,"Email dan Password tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else if(email.isNotEmpty() || password.isNotEmpty()){
                showLoading()
                postLogin(email,password)
                showSnackBar()
//                viewModel.accountLogin()
                toHome()

            }

//            viewModel.saveSession(UserModel(email, "sample_token"))
//            AlertDialog.Builder(this).apply {
//                setTitle("Yeah!")
//                setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
//                setPositiveButton("Lanjut") { _, _ ->
//                    val intent = Intent(context, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    finish()
//                }
//                create()
//                show()
//            }
        }
    }

    private fun showLoading(){
        viewModel.isLoading.observe(this@LoginActivity){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun postLogin(email:String, password:String){
        viewModel.login(email, password)
        viewModel.loginResponse.observe(this@LoginActivity){
            saveSession(
                UserModel(
                    it.loginResult?.name.toString(),
                    it.loginResult?.token.toString(),
                    true
                )
            )
        }
    }

    private fun saveSession(session:UserModel){
        viewModel.saveSession(session)
    }

    private fun showSnackBar(){
        viewModel.snackbarText.observe(this@LoginActivity){
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toHome() {
        viewModel.loginResponse.observe(this@LoginActivity) {
            if (it.error == false) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }

}