package com.test.assignment.ui.favourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(model: FavoriteModel?)

    @get:Query("SELECT * FROM favorite_table")
    val allData: List<FavoriteModel?>?

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_table WHERE id = :id)")
    fun isFavorite(id: Int): Boolean

    @Query("DELETE FROM favorite_table WHERE id = :id")
    fun deleteData(id: Int)
}