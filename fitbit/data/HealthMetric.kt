package com.example.fitbit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_entry")
data class DailyEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val foodName: String,
    val calories: Int,
    val waterIntake: Int,
    val date: String
)