package com.test.assignment.ui.favourite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_table",
    indices = [Index(value = ["title"], unique = true), Index(
        value = ["imageURL"],
        unique = true
    ), Index(value = ["ratingCount"], unique = true),Index(value = ["price"], unique = true)]
)
class FavoriteModel(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true) var id: Int,
    @field:ColumnInfo(name = "title") var title: String,
    @field:ColumnInfo(name = "imageURL") var imageURL: String,
    @field:ColumnInfo(name = "ratingCount") var ratingCount: Double,
    @field:ColumnInfo(name = "price") var price: Double
)
