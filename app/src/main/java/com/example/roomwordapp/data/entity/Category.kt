package com.example.roomwordapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long?,      // Unique identifier for the category
    val name: String    // Name of the category (e.g., "Food", "Transportation", etc.)
)