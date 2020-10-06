package com.byjus.headlines.headlines.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.byjus.headlines.headlines.models.response.ArticlesItem


@Dao
interface DAOHeadline {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: ArticlesItem?)

    @Query("DELETE FROM headline_news")
    fun deleteAll()

    @Query("SELECT * from headline_news")
    fun getALLCache(): List<ArticlesItem>?

}