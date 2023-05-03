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
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteLocalRepository(database: NoteDatabase): NoteLocalRepository {
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