package com.example.studymate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.network.QuoteApiService
import com.example.studymate.ui.assignments.AssignmentAdapter
import com.example.studymate.viewmodel.AssignmentViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val assignmentViewModel: AssignmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvQuote = view.findViewById<TextView>(R.id.tvQuote)
        val tvAuthor = view.findViewById<TextView>(R.id.tvAuthor)
        val rvUpcoming = view.findViewById<RecyclerView>(R.id.rvUpcomingAssignments)

        // Set up adapter for upcoming assignments
        val adapter = AssignmentAdapter(
            onDelete = { assignment ->
                assignmentViewModel.deleteAssignment(assignment)
            },
            onComplete = { assignment ->
                assignmentViewModel.updateAssignment(
                    assignment.copy(isCompleted = !assignment.isCompleted)
                )
            }
        )

        rvUpcoming.layoutManager = LinearLayoutManager(requireContext())
        rvUpcoming.adapter = adapter

        // Observe assignments and show only incomplete ones
        lifecycleScope.launch {
            assignmentViewModel.assignments.collect { assignments ->
                val upcoming = assignments.filter { !it.isCompleted }
                adapter.submitList(upcoming)
            }
        }

        // Fetch quote from API
        lifecycleScope.launch {
            try {
                val quotes = QuoteApiService.create().getRandomQuote()
                if (quotes.isNotEmpty()) {
                    tvQuote.text = "\"${quotes[0].q}\""
                    tvAuthor.text = "— ${quotes[0].a}"
                }
            } catch (e: Exception) {
                tvQuote.text = "Stay focused and keep going!"
                tvAuthor.text = ""
            }
        }
    }
}