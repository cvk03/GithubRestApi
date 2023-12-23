package com.example.androidtask.data_fetching

data class ApiResponse<T>(
    val data: T
)
data class Issue(

    val closed_at: Any,
    val created_at: String,
    val state: String,
    val title: String,
    val user: User
)