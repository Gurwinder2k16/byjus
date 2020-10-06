package com.byjus.headlines.headlines.models.response

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName


data class HeadlineResponse(
    @field:SerializedName("totalResults") val totalResults: Int? = null,
    @field:SerializedName("articles") val articles: List<ArticlesItem>? = ArrayList(),
    @field:SerializedName("status") val status: String? = null
)

@Entity(tableName = "headline_news")
data class ArticlesItem(
    @field:SerializedName("publishedAt") val publishedAt: String? = null,
    @field:SerializedName("author") val author: String? = null,
    @field:SerializedName("urlToImage") val urlToImage: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("source") val source: Source,
    @NonNull @PrimaryKey @field:SerializedName("title") val title: String = "",
    @field:SerializedName("url") val url: String? = null,
    @field:SerializedName("content") val content: String? = null
)

@Entity
data class Source(
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("id") val id: Any? = null
)
