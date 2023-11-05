package com.dicoding.picodiploma.loginwithanimation.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.autofill.Dataset
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailActivity
import com.dicoding.picodiploma.loginwithanimation.view.story.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private var token: String? = null
    private lateinit var adapter: UserStoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewContent()
        setupUser()
        getDataAdapter()
        setupView()
        setupAction()
        playAnimation()



    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.getStories
//    }

    private fun setupViewContent(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun setupUser() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                binding.nameTextView.text = user.email
                token = user.token
                println("nama email: ${user.email}")
                println("nama token Anda: ${user.token}")
                viewModel.getStories.observe(this@MainActivity) {
                    adapter.submitData(lifecycle, it)
                }
                binding.progressBar.visibility = View.GONE

            }
        }
        showSnackBar()
    }

    private fun getDataAdapter() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUsers.layoutManager = layoutManager
        adapter = UserStoryAdapter()
        binding.rvUsers.adapter = adapter.withLoadStateFooter(
            footer=LoadingStateAdapter{
                adapter.retry()
            }
        )
    }

    private fun showLoading() {
        viewModel.isLoading.observe(this@MainActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showSnackBar() {
        viewModel.snackbarText.observe(this@MainActivity) {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.nameTextView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }


        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(name, message, logout)
            startDelay = 100
            start()
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
        supportActionBar?.setTitle("Dicoding Stories")
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            token = null
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

}