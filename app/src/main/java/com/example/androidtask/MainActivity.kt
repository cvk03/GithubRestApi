package com.example.androidtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.Model.MainViewModel
import com.example.androidtask.Model.MainViewModelFactory
import com.example.androidtask.data_fetching.Issue
import com.example.androidtask.data_fetching.IssueService
import com.example.androidtask.data_fetching.RetrofitHelper

class MainActivity : AppCompatActivity() {

    lateinit var rv_issues : RecyclerView
    lateinit var issuesAdapter : IssuesAdapter
    lateinit var searchView : SearchView
    lateinit var state : String
    lateinit var mainViewModel: MainViewModel
    lateinit var issueService : IssueService
    lateinit var repository : IssueRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up SearchView
        searchView = findViewById(R.id.searchView)

        //instantiating the state
        state = "closed"

        issuesAdapter = IssuesAdapter(emptyList(), this)
        //recyclerView and adapter
        rv_issues = findViewById(R.id.rv_issues)
        rv_issues.layoutManager = LinearLayoutManager(this)
        rv_issues.adapter = issuesAdapter

        getRequiredIssues(state)


    }

    fun getRequiredIssues(state : String)
    {
        //data retrieval
        issueService = RetrofitHelper.get_instance().create(IssueService::class.java)

        repository = IssueRepository(issueService)

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository,state)).get(MainViewModel::class.java)
        
        mainViewModel.issues.observe(this, Observer {issues->

            if (issues.isNotEmpty()) {
                Log.d("MainActivity", issues[0].title)
                issuesAdapter = IssuesAdapter(issues,this)
                rv_issues.adapter = issuesAdapter

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        issuesAdapter.filter(newText.orEmpty())
                        return true
                    }
                })
            }

        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.open -> {
                state = "open"
                getRequiredIssues(state)
                Toast.makeText(this,"Open Issues are loaded successfully",Toast.LENGTH_SHORT).show()
            }
            R.id.closed ->{
                state = "closed"
                getRequiredIssues(state)
                Toast.makeText(this,"Closed Issues are loaded successfully",Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}