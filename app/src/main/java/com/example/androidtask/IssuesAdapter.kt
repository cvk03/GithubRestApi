package com.example.androidtask

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.androidtask.data_fetching.Issue
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class IssuesAdapter(private var originalIssues: List<Issue>,private val context: Context) : RecyclerView.Adapter<MyViewHolder>() {

    private var filteredIssues: List<Issue> = originalIssues

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_issue, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filteredIssues.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = filteredIssues[position]


            val issue = item
            holder.tv_title.text = issue.title
            holder.tv_user.text = "User : " + issue.user.login
            holder.tv_created_date.text = formatDateString(issue.created_at)
            holder.tv_closed_date.text = formatDateString(issue.closed_at.toString())
            Glide.with(context)
                .load(issue.user.avatar_url)
                .into(holder.iv_user)

    }

    fun filter(query: String) {
        filteredIssues = if (query.isEmpty()) {
            originalIssues
        } else {
            originalIssues.filter { issue ->
                issue.title.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    fun updateIssues(newIssues: List<Issue>) {
        originalIssues = newIssues
        filter("")
    }


    fun formatDateString(originalDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            val date = inputFormat.parse(originalDate)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return originalDate
    }
}
class MyViewHolder(itemView: View) : ViewHolder(itemView){

    val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
    val tv_user = itemView.findViewById<TextView>(R.id.tv_user)
    val iv_user = itemView.findViewById<ImageView>(R.id.iv_user)
    val tv_created_date = itemView.findViewById<TextView>(R.id.tv_created_date)
    val tv_closed_date = itemView.findViewById<TextView>(R.id.tv_closed_date)

}