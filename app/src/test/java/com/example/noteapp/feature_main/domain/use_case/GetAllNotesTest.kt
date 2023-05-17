package com.example.noteapp.feature_main.domain.use_case

import com.example.noteapp.feature_main.data.repository.DummyLocalRepository
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository
import com.example.noteapp.feature_main.domain.util.OrderBy
import com.example.noteapp.feature_main.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllNotesTest {

    private lateinit var getAllNotes: GetAllNotes
    private lateinit var repository: NoteLocalRepository

    @Before
    fun setUp() {
        repository = DummyLocalRepository()
        getAllNotes = GetAllNotes(repository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    createTs = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach {
                repository.insert(it)
            }
        }
    }

    @Test
    fun `Order notes by title asc, correct order`() = runBlocking {
        val notes = getAllNotes(OrderBy.Title(OrderType.Asc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by title desc, correct order`() = runBlocking {
        val notes = getAllNotes(OrderBy.Title(OrderType.Desc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by color asc, correct order`() = runBlocking {
        val notes = getAllNotes(OrderBy.Color(OrderType.Asc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by color desc, correct order`() = runBlocking {
        val notes = getAllNotes(OrderBy.Color(OrderType.Desc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by date asc, correct order`() = runBlocking {
        val notes = getAllNotes(OrderBy.Date(OrderType.Asc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].createTs).isLessThan(notes[i + 1].createTs)
        }
    }

    @Test
    fun `Order notes by date desc, correct order`() = runBlocking {
        val notes = getAllNotes(OrderBy.Date(OrderType.Desc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].createTs).isGreaterThan(notes[i + 1].createTs)
        }
    }
}