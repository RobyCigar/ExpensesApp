package com.example.roomwordapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.roomwordapp.data.entity.Ledger

@Dao
interface LedgerDao {
    @Query("SELECT * FROM ledger")
    fun getAll(): List<Ledger>

    @Query("SELECT * FROM ledger WHERE id IN (:ledgerIds)")
    fun loadAllByIds(ledgerIds: IntArray): List<Ledger>

    @Insert
    fun insertAll(vararg ledgers: Ledger)

    @Delete
    fun delete(ledger: Ledger)
}