package com.dsg.demo.ui.main.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsg.demo.R.*
import com.dsg.demo.data.api.ApiHelper
import com.dsg.demo.data.api.RetrofitBuilder
import com.dsg.demo.data.model.Event
import com.dsg.demo.data.model.EventList
import com.dsg.demo.ui.base.ViewModelFactory
import com.dsg.demo.ui.main.adapter.MainAdapter
import com.dsg.demo.ui.main.viewmodel.MainViewModel
import com.dsg.demo.utils.Status.ERROR
import com.dsg.demo.utils.Status.LOADING
import com.dsg.demo.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        /*recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )*/
        recyclerView.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.length > 0) {
                    txtCancelSearch.visibility = View.VISIBLE
                    if (s!!.length > 3)
                    {
                        setupObservers(s.toString())
                    }
                }
                else {
                    txtCancelSearch.visibility = View.GONE
                }
            }

        })

        txtCancelSearch.setOnClickListener{
            etSearch.setText("")
            setupObservers("")
        }

    }

    private fun setupObservers(search: String = "") {
        viewModel.getEvents(search).observe(this, Observer {
            it?.let { resource ->
               when (resource.status) {
                    SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        /*resource.data?.let { users -> retrieveList(users) }*/
                        Log.e("Response", "data ${resource.data.toString()}")
                        retrieveList(resource.data!!)
                    }
                    ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: EventList)
    {
        val events: List<Event> = users.events
        adapter.apply {
            addUsers(events)
            notifyDataSetChanged()
        }
    }
}
