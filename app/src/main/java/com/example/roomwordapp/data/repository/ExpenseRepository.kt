package com.example.roomwordapp.data.repository

import androidx.annotation.WorkerThread
import com.example.roomwordapp.data.entity.Expense
import com.example.roomwordapp.data.dao.ExpenseDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ExpenseRepository(private val wordDao: ExpenseDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Expense>> = wordDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Expense) {
        wordDao.insert(word)
    }
}