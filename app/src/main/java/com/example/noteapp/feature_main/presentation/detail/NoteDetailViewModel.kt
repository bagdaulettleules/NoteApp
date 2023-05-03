package com.example.noteapp.feature_main.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_main.domain.model.InvalidNoteException
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.use_case.NoteUseCases
import com.example.noteapp.feature_main.presentation.util.UiEvent
import com.example.noteapp.feature_main.presentation.util.defaultColorSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _title = mutableStateOf(
        TextFieldState(
            hint = "Title"
        )
    )
    val title: State<TextFieldState> = _title

    private val _content = mutableStateOf(
        TextFieldState(
            hint = "Content"
        )
    )
    val content: State<TextFieldState> = _content

    private val _color = mutableStateOf(
        defaultColorSet.random().toArgb()
    )
    val color: State<Int> = _color

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow

    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.get(noteId)?.also { note ->
                        currentId = note.id
                        _title.value = title.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _content.value = content.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _color.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.ColorChanged -> {
                _color.value = event.color
            }
            is NoteDetailEvent.ContentChanged -> {
                _content.value = content.value.copy(
                    text = event.value
                )
            }
            is NoteDetailEvent.ContentFocusChanged -> {
                _content.value = content.value.copy(
                    isHintVisible = !event.focusState.isFocused && content.value.text.isBlank()
                )
            }
            is NoteDetailEvent.TitleChanged -> {
                _title.value = title.value.copy(
                    text = event.value
                )
            }
            is NoteDetailEvent.TitleFocusChanged -> {
                _title.value = title.value.copy(
                    isHintVisible = !event.focusState.isFocused && title.value.text.isBlank()
                )
            }
            NoteDetailEvent.NoteSaved -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.save(
                            Note(
                                title = title.value.text,
                                content = content.value.text,
                                createTs = System.currentTimeMillis(),
                                color = color.value,
                                id = currentId
                            )
                        )
                        _eventFlow.emit(UiEvent.SuccessfullyCompleted)
                    } catch (ex: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ErrorOccurred(
                                message = ex.message ?: "Note couldn't be saved"
                            )
                        )
                    }
                }
            }
        }
    }
}