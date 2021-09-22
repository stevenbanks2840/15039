package com.ftresearch.cakes.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cakes")
data class CakeEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "desc")
    var desc: String,

    @ColumnInfo(name = "detail")
    var detail: String,

    @ColumnInfo(name = "image")
    var image: String?
)
