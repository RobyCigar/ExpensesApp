package com.example.roomwordapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.roomwordapp.data.entity.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAll(): List<Account>

    @Query("SELECT * FROM account WHERE id IN (:accountId)")
    fun loadAllByIds(accountId: IntArray): List<Account>

    @Insert
    fun insertAll(vararg accounts: Account)

    @Delete
    fun delete(account: Account)
}