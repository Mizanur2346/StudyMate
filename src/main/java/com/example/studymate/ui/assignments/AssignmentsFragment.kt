package com.example.studymate.ui.assignments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.data.model.Assignment
import com.example.studymate.viewmodel.AssignmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class AssignmentsFragment : Fragment() {

    private val viewModel: AssignmentViewModel by activityViewModels()
    private lateinit var adapter: AssignmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_assignments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvAssignments)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddAssignment)

        adapter = AssignmentAdapter(
            onDelete = { assignment ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Assignment")
                    .setMessage("Are you sure you want to delete this assignment?")
                    .setPositiveButton("Delete") { _, _ ->
                        viewModel.deleteAssignment(assignment)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            },
            onComplete = { assignment ->
                viewModel.updateAssignment(assignment.copy(isCompleted = !assignment.isCompleted))
            }
        )

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        lifecycleScope.launch {
            viewModel.assignments.collect { assignments ->
                adapter.submitList(assignments)
            }
        }

        fab.setOnClickListener {
            showAddAssignmentDialog()
        }
    }

    private fun showAddAssignmentDialog() {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }
        val etTitle = EditText(requireContext()).apply { hint = "Title" }
        val etSubject = EditText(requireContext()).apply { hint = "Subject" }
        val etDueDate = EditText(requireContext()).apply { hint = "Due Date (e.g. 2026-05-01)" }
        val etPriority = EditText(requireContext()).apply { hint = "Priority (High/Medium/Low)" }

        layout.addView(etTitle)
        layout.addView(etSubject)
        layout.addView(etDueDate)
        layout.addView(etPriority)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Assignment")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val assignment = Assignment(
                    title = etTitle.text.toString(),
                    subject = etSubject.text.toString(),
                    dueDate = etDueDate.text.toString(),
                    priority = etPriority.text.toString()
                )
                viewModel.addAssignment(assignment)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}