package com.example.roomwordapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "expenses",
    foreignKeys = [ForeignKey(
    entity = Category::class,
    childColumns = ["category_id"],
    parentColumns = ["id"]
)])
class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int?= null,
    @ColumnInfo(name = "title") val title: String? = "",
    @ColumnInfo(name = "description") val description: String? = "",
    @ColumnInfo(name = "amount") val amount: Int? = 0,
    @ColumnInfo(name = "category_id", index = true) val categoryId: Long? = null
)
