package com.example.noteapp.feature_main.domain.use_case

import com.example.noteapp.feature_main.data.repository.DummyLocalRepository
import com.example.noteapp.feature_main.domain.model.InvalidNoteException
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SaveNoteTest {
    private lateinit var repository: NoteLocalRepository
    private lateinit var saveNote: SaveNote

    @Before
    fun setUp() {
        repository = DummyLocalRepository()
        saveNote = SaveNote(repository)
    }

    @Test
    fun `Saving note with empty title is not allowed`() = runBlocking {
        val note = Note(
            title = "",
            content = "Qwert",
            createTs = 1L,
            color = 1
        )
        assertThrows(InvalidNoteException::class.java) {
            coVerify { saveNote(note) }
        }
        return@runBlocking
    }

    @Test
    fun `Saving note with empty content is not allowed`() = runBlocking {
        val note = Note(
            title = "Qwert",
            content = "",
            createTs = 1L,
            color = 1
        )
        assertThrows(InvalidNoteException::class.java) {
            coVerify { saveNote(note) }
        }
        return@runBlocking
    }

    @Test
    fun `Saving valid note`() = runBlocking {
        val note = Note(
            title = "Qwert",
            content = "Qwerty",
            createTs = 1L,
            color = 1,
            id = 1234
        )
        saveNote(note)

        val insertedNote = repository.getById(note.id!!)
        assertEquals(note, insertedNote)
    }
}