package com.test.assignment.ui.favourite

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [FavoriteModel::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract val dao: FavoriteDao?

    companion object {
        private var instance: FavoriteDatabase? = null
        fun getDatabase(context: Context?): FavoriteDatabase? {
            if (instance == null) {
                synchronized(FavoriteDatabase::class.java) {
                    instance = databaseBuilder(
                        context!!, FavoriteDatabase::class.java, "DATABASE"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}