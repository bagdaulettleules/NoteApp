package com.example.noteapp.feature_main.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.domain.use_case.NoteUseCases
import com.example.noteapp.feature_main.domain.util.OrderBy
import com.example.noteapp.feature_main.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _state = mutableStateOf(NotesListState())
    val state: State<NotesListState> = _state

    private var getNotesJob: Job? = null

    private var deletedNote: Note? = null

    init {
        getNotes(OrderBy.Date(OrderType.Desc))
    }

    fun onEvent(event: NotesListEvent) {
        when (event) {
            is NotesListEvent.Delete -> {
                viewModelScope.launch {
                    noteUseCases.delete(event.note)
                    deletedNote = event.note
                }
            }
            is NotesListEvent.Order -> {
                if (state.value.orderBy::class == event.orderBy::class &&
                    state.value.orderBy.orderType == event.orderBy.orderType::class
                ) {
                    return
                }
                getNotes(event.orderBy)
            }
            NotesListEvent.Restore -> {
                viewModelScope.launch {
                    noteUseCases.save(deletedNote ?: return@launch)
                    deletedNote = null
                }
            }
            is NotesListEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = event.isVisible
                )
            }
        }
    }

    private fun getNotes(orderBy: OrderBy) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getAll(orderBy)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    orderBy = orderBy
                )
            }
            .launchIn(viewModelScope)
    }
}