package com.example.mviapplication.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mviapplication.R
import com.example.mviapplication.data.api.ApiHelperImpl
import com.example.mviapplication.data.api.RetrofitBuilder
import com.example.mviapplication.data.model.User
import com.example.mviapplication.ui.main.adapter.MainAdapter
import com.example.mviapplication.ui.main.intent.MainIntent
import com.example.mviapplication.ui.main.viewmodel.MainViewModel
import com.example.mviapplication.ui.main.viewstate.MainState
import com.example.mviapplication.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.BufferedReader

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupUI() {
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.run {
            addItemDecoration(
                DividerItemDecoration(
                    rv.context,
                    (rv.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        adapter = MainAdapter(this , arrayListOf())
        rv.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        ).get(MainViewModel::class.java)
    }

    private fun observeViewModel() {
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val buttonFetchUser: Button = findViewById(R.id.buttonFetchUser)
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        findViewById<RecyclerView>(R.id.recyclerView).visibility = View.VISIBLE
        users.let { listOfUsers ->
            listOfUsers.let {
                adapter.addData(it)
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun setupClicks(){
        findViewById<Button>(R.id.buttonFetchUser).setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }
}