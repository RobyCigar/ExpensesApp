package com.example.roomwordapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Long,          // Unique identifier for the bank account
    val accountNumber: String,  // Bank account number
    val accountHolder: String,  // Name of the account holder
    val bankName: String   // Name of the bank
)