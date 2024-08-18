package com.biprangshu.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.biprangshu.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)


    @Delete
    suspend fun delete(article: Article)

    @androidx.room.Query("Select * From Article")
    fun getArticles(): Flow<List<Article>>

}