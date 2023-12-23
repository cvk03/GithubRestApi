package com.example.androidtask.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtask.IssueRepository

class MainViewModelFactory(private val repository : IssueRepository,private val state: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(state,repository) as T
    }
}