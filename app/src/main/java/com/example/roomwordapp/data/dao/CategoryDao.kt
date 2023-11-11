package com.example.roomwordapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.roomwordapp.data.entity.Category
import kotlinx.coroutines.flow.Flow
@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM category WHERE id IN (:categoryId)")
    fun loadAllByIds(categoryId: IntArray): List<Category>

    @Insert
    fun insert(vararg categories: Category)

    @Delete
    fun delete(category: Category)
}