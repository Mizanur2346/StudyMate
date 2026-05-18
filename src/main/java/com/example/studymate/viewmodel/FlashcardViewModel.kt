package com.example.studymate.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymate.data.database.StudyMateDatabase
import com.example.studymate.data.model.Flashcard
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlashcardViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = StudyMateDatabase.getDatabase(application).flashcardDao()

    val flashcards = dao.getAllFlashcards()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addFlashcard(flashcard: Flashcard) = viewModelScope.launch {
        dao.insertFlashcard(flashcard)
    }

    fun updateFlashcard(flashcard: Flashcard) = viewModelScope.launch {
        dao.updateFlashcard(flashcard)
    }

    fun deleteFlashcard(flashcard: Flashcard) = viewModelScope.launch {
        dao.deleteFlashcard(flashcard)
    }
}