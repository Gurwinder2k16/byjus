package com.byjus.headlines.headlines.dao

import android.content.Context
import androidx.room.*
import com.byjus.headlines.headlines.models.response.ArticlesItem
import com.byjus.headlines.headlines.models.response.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(entities = [ArticlesItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HeadLineDatabase : RoomDatabase() {
    abstract fun DAOHeadline(): DAOHeadline?

    companion object {
        @Volatile
        private var INSTANCE: HeadLineDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): HeadLineDatabase? {
            if (INSTANCE == null) {
                synchronized(HeadLineDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            HeadLineDatabase::class.java,
                            "headline_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}

class Converters {
    @TypeConverter
    fun fromString(value: String?): Source? {
        val listType: Type = object : TypeToken<Source>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayLisr(list: Source): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}