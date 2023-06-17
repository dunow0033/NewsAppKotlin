package com.example.newsappkotlin.ui.fragments

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsappkotlin.OnFetchDataListener
import com.example.newsappkotlin.adapters.NewsAdapter
import com.example.newsappkotlin.data.model.NewsApiResponse
import com.example.newsappkotlin.data.model.NewsHeadlines
import com.example.newsappkotlin.data.remote.NewsManager
import com.example.newsappkotlin.databinding.FragmentMainBinding
import com.example.newsappkotlin.repository.NewsRepository
import com.example.newsappkotlin.viewmodels.NewsViewModel
import com.example.newsappkotlin.viewmodels.NewsViewModelFactory

class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    var dialog: Dialog? = null
    var b1: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    var b4: Button? = null
    var b5: Button? = null

    var b6: Button? = null
    var b7: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

//        dialog = Dialog(requireContext())
//        dialog!!.setTitle("Fetching news articles....")
//        dialog!!.show()

        b1 = binding.btn1
        b1!!.setOnClickListener(this)

        b2 = binding.btn2
        b2!!.setOnClickListener(this)

        b3 = binding.btn3
        b3!!.setOnClickListener(this)

        b4 = binding.btn4
        b4!!.setOnClickListener(this)

        b5 = binding.btn5
        b5!!.setOnClickListener(this)

        b6 = binding.btn6
        b6!!.setOnClickListener(this)

        b7 = binding.btn7
        //b7!!.setOnClickListener(this)
        b7!!.setOnClickListener{
            //listener.onFetchData(null, null)
            Toast.makeText(requireContext(), "No data found!!", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

//        newsViewModel = ViewModelProvider(
//            this,
//            NewsViewModelFactory(NewsRepository(NewsManager()), "general", null)
//        ).get(NewsViewModel::class.java)

        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(listener, NewsRepository(NewsManager()), "general")
        ).get(NewsViewModel::class.java)

        newsViewModel.newsResponse.observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it.data)
        })
    }

    private val listener: OnFetchDataListener<NewsApiResponse> = object : OnFetchDataListener<NewsApiResponse> {
            override fun onFetchData(list: List<NewsHeadlines>, message: String) {
                if (list.isEmpty()) {
                    Toast.makeText(requireContext(), "No data found!!", Toast.LENGTH_LONG).show()
                } else {
                    setupRecyclerView()
                   // dialog!!.dismiss()
                }
            }

            override fun onError(message: String) {
                Toast.makeText(requireContext(), "An Error Occurred!!", Toast.LENGTH_LONG).show()
            }
        }

    private fun setupRecyclerView() = binding.recyclerMain.apply {
//        newsAdapter = NewsAdapter()
//        adapter = todoAdapter
//        layoutManager = LinearLayoutManager(requireContext())

        setHasFixedSize(true)
        layoutManager = GridLayoutManager(requireContext(), 1)
        newsAdapter = NewsAdapter(requireContext())
        adapter = newsAdapter
    }

    override fun onClick(view: View) {
        val button = view as Button
        val category = button.text.toString()
//        dialog!!.setTitle("Fetching news articles of $category")
//        dialog!!.show()

        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(listener, NewsRepository(NewsManager()), category)
        ).get(NewsViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}