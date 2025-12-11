package com.example.fitbit.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyEntryDao {
    @Query("SELECT * FROM daily_entry ORDER BY date DESC")
    fun getAll(): Flow<List<DailyEntry>>

    @Insert
    suspend fun insert(dailyEntry: DailyEntry)

    @Delete
    suspend fun delete(dailyEntry: DailyEntry)

    @Query("SELECT SUM(calories) FROM daily_entry")
    fun getTotalCalories(): Flow<Int?>

    @Query("SELECT SUM(waterIntake) FROM daily_entry")
    fun getTotalWaterIntake(): Flow<Int?>

    @Query("SELECT AVG(calories) FROM daily_entry")
    fun getAverageCalories(): Flow<Double?>

    @Query("SELECT AVG(waterIntake) FROM daily_entry")
    fun getAverageWaterIntake(): Flow<Double?>

    @Query("SELECT MIN(calories) FROM daily_entry")
    fun getMinCalories(): Flow<Int?>

    @Query("SELECT MAX(calories) FROM daily_entry")
    fun getMaxCalories(): Flow<Int?>
}