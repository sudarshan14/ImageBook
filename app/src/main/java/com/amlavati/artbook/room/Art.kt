package com.amlavati.artbook.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Art(
    var naem: String,
    var artistName: String,
    var year: Int,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
