package com.example.roomwordapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
class Ledger(
    @PrimaryKey(autoGenerate = true) val id: Int?= null,         // Unique identifier for the ledger entry
    val expenseId: Long,  // ID of the associated expense
    val accountId: Long,   // ID of the associated bank account
    val transactionType: TransactionType, // Type of transaction (e.g., Debit, Credit)
    val amount: Double,    // Amount of the transaction
    val date: Date         // Date of the transaction
)

enum class TransactionType {
    DEBIT,
    CREDIT
}