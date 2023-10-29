package com.example.roomwordapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
class Word(
    @PrimaryKey(autoGenerate = true) val id: Int?= null,
    @ColumnInfo(name = "title") val title: String? = "",
    @ColumnInfo(name = "description") val description: String? = "",
    @ColumnInfo(name = "amount") val amount: Int? = 0,
)
