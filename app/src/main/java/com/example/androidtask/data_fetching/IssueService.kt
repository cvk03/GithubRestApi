package com.example.androidtask.data_fetching

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IssueService {

    @GET("repos/google-developer-training/android-basics-kotlin-mars-photos-app/issues")
    suspend fun getIssues(
        @Query("state") state: String): Response<List<Issue>>
}