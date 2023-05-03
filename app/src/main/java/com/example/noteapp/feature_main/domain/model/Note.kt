package com.example.noteapp.feature_main.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    val title: String,
    val content: String,
    val createTs: Long,
    val color: Int,
    val isFavourite: Boolean = false,
    @PrimaryKey val id: Int? = null
) {
}
