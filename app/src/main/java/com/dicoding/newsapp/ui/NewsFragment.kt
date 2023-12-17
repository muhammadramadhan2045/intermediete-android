package com.dicoding.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.newsapp.data.Result
import com.dicoding.newsapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var tabName: String? = null

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)


        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel :NewsViewModel by viewModels { factory }


        val newsAdapter = NewsAdapter{news->
            if(news.isBookmarked){
                viewModel.deleteNews(news)
                Toast.makeText(context,"Berita dihapus dari bookmark",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.saveNews(news)
                Toast.makeText(context,"Berita ditambahkan ke bookmark",Toast.LENGTH_SHORT).show()
            }
        }

        if (tabName == TAB_NEWS) {
            viewModel.getHeadlinesNews().observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val newsData = result.data
                            newsAdapter.submitList(newsData)
                        }
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }else if(tabName == TAB_BOOKMARK){
            viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookMarkNews->
                binding?.progressBar?.visibility = View.GONE
                newsAdapter.submitList(bookMarkNews)
            }
        }



        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }
}