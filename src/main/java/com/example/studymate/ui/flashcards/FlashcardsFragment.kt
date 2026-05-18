package com.example.studymate.ui.flashcards

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
import com.example.studymate.data.model.Flashcard
import com.example.studymate.viewmodel.FlashcardViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class FlashcardsFragment : Fragment() {

    private val viewModel: FlashcardViewModel by activityViewModels()
    private lateinit var adapter: FlashcardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flashcards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvFlashcards)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddFlashcard)

        adapter = FlashcardAdapter(
            onDelete = { flashcard ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Flashcard")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Delete") { _, _ ->
                        viewModel.deleteFlashcard(flashcard)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        lifecycleScope.launch {
            viewModel.flashcards.collect { flashcards ->
                adapter.submitList(flashcards)
            }
        }

        fab.setOnClickListener {
            showAddFlashcardDialog()
        }
    }

    private fun showAddFlashcardDialog() {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }
        val etQuestion = EditText(requireContext()).apply { hint = "Question" }
        val etAnswer = EditText(requireContext()).apply { hint = "Answer" }
        val etSubject = EditText(requireContext()).apply { hint = "Subject" }

        layout.addView(etQuestion)
        layout.addView(etAnswer)
        layout.addView(etSubject)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Flashcard")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val flashcard = Flashcard(
                    question = etQuestion.text.toString(),
                    answer = etAnswer.text.toString(),
                    subject = etSubject.text.toString()
                )
                viewModel.addFlashcard(flashcard)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}