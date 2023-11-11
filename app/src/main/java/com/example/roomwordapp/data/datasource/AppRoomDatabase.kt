package com.example.roomwordapp.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomwordapp.data.dao.AccountDao
import com.example.roomwordapp.data.dao.CategoryDao
import com.example.roomwordapp.data.entity.User
import com.example.roomwordapp.data.dao.UserDao
import com.example.roomwordapp.data.entity.Expense
import com.example.roomwordapp.data.dao.ExpenseDao
import com.example.roomwordapp.data.entity.Account
import com.example.roomwordapp.data.entity.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Expense::class, User::class, Category::class, Account::class], version = 11, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun accountDao(): AccountDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.expenseDao())
                    }
                }
            }

            suspend fun populateDatabase(expenseDao: ExpenseDao) {
                // Delete all content here.
                expenseDao.deleteAll()
            }
        }
    }
}