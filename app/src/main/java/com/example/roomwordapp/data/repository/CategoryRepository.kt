package com.example.roomwordapp.data.repository

import androidx.annotation.WorkerThread
import com.example.roomwordapp.data.dao.CategoryDao
import com.example.roomwordapp.data.entity.Expense
import com.example.roomwordapp.data.dao.ExpenseDao
import com.example.roomwordapp.data.entity.Category
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class CategoryRepository(private val categoryDao: CategoryDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allCategories: Flow<List<Category>> = categoryDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Category) {
        categoryDao.insert(word)
    }
}