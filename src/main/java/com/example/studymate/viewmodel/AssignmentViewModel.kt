package com.example.studymate.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymate.data.database.StudyMateDatabase
import com.example.studymate.data.model.Assignment
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AssignmentViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = StudyMateDatabase.getDatabase(application).assignmentDao()

    val assignments = dao.getAllAssignments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAssignment(assignment: Assignment) = viewModelScope.launch {
        dao.insertAssignment(assignment)
    }

    fun updateAssignment(assignment: Assignment) = viewModelScope.launch {
        dao.updateAssignment(assignment)
    }

    fun deleteAssignment(assignment: Assignment) = viewModelScope.launch {
        dao.deleteAssignment(assignment)
    }
}