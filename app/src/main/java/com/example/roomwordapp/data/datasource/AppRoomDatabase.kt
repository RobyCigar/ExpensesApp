package com.example.roomwordapp.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomwordapp.data.dao.AccountDao
import com.example.roomwordapp.data.dao.CategoryDao
import com.example.roomwordapp.data.dao.LedgerDao
import com.example.roomwordapp.data.entity.User
import com.example.roomwordapp.data.dao.UserDao
import com.example.roomwordapp.data.entity.Word
import com.example.roomwordapp.data.dao.WordDao
import com.example.roomwordapp.data.entity.Account
import com.example.roomwordapp.data.entity.Category
import com.example.roomwordapp.data.entity.Ledger
import com.example.roomwordapp.util.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@TypeConverters(DateConverter::class)
@Database(entities = [Word::class, User::class, Account::class, Category::class, Ledger::class], version = 5, exportSchema = false)
public abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun userDao(): UserDao
    abstract fun  accountDao(): AccountDao
    abstract fun  categoryDao(): CategoryDao
    abstract fun  ledgerDao(): LedgerDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "app_database"
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
                        populateDatabase(database.wordDao())
                    }
                }
            }

            suspend fun populateDatabase(wordDao: WordDao) {
                // Delete all content here.
                wordDao.deleteAll()
                // TODO: Add your own words!
            }
        }
    }
}