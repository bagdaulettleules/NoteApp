package com.example.noteapp.di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.feature_main.data.data_source.NoteDatabase
import com.example.noteapp.feature_main.data.repository.NoteLocalRepositoryImpl
import com.example.noteapp.feature_main.domain.repository.NoteLocalRepository
import com.example.noteapp.feature_main.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteLocalRepository(database: NoteDatabase): NoteLocalRepository {
        return NoteLocalRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteLocalRepository): NoteUseCases {
        return NoteUseCases(
            getAll = GetAllNotes(repository),
            get = GetNote(repository),
            save = SaveNote(repository),
            delete = DeleteNote(repository)
        )
    }
}