package com.example.noteapp.feature_main.data.data_source

import androidx.room.*
import com.example.noteapp.feature_main.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
}