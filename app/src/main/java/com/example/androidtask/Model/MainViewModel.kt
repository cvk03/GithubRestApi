package com.example.androidtask.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtask.IssueRepository
import com.example.androidtask.data_fetching.Issue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val state: String,private val repository : IssueRepository) : ViewModel() {

   init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getIssues(state)
        }

    }

    val issues: LiveData<List<Issue>> = repository.issues

}

