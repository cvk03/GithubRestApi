package com.example.androidtask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidtask.data_fetching.Issue
import com.example.androidtask.data_fetching.IssueService

class IssueRepository(val issueService: IssueService) {

    private val _issues = MutableLiveData<List<Issue>>()
    val issues: LiveData<List<Issue>> get() = _issues

    //pagination
    private var currentPage = 1
    private val perPage = 6


    suspend fun getIssues(state:String) {
        val response = issueService.getIssues(state)

        if (response.isSuccessful) {
            //currentPage++
            _issues.postValue(response.body())
        } else {
            Log.e("IssueRepository", "Failed to fetch issues. Code: ${response.code()}")
        }
    }

}
