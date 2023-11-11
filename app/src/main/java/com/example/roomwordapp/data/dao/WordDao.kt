package com.example.roomwordapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomwordapp.data.entity.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses ORDER BY title ASC")
    fun getAlphabetizedWords(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getWord(id: Int): Flow<Expense>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Expense)

    @Query("DELETE FROM expenses")
    fun deleteAll(): Int

    @Query("DELETE FROM expenses WHERE id = :id")
    fun deleteByUserId(id: Int)
}