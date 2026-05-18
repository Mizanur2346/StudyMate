package com.example.studymate.ui.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.data.model.Flashcard

class FlashcardAdapter(
    private val onDelete: (Flashcard) -> Unit
) : ListAdapter<Flashcard, FlashcardAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvQuestion: TextView = view.findViewById(R.id.tvQuestion)
        val tvAnswer: TextView = view.findViewById(R.id.tvAnswer)
        val tvSubject: TextView = view.findViewById(R.id.tvSubject)
        val btnDelete: Button = view.findViewById(R.id.btnDeleteFlashcard)
        val btnFlip: Button = view.findViewById(R.id.btnFlip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flashcard = getItem(position)
        holder.tvQuestion.text = flashcard.question
        holder.tvAnswer.text = flashcard.answer
        holder.tvSubject.text = flashcard.subject
        holder.tvAnswer.visibility = View.GONE

        holder.btnFlip.setOnClickListener {
            if (holder.tvAnswer.visibility == View.GONE) {
                holder.tvAnswer.visibility = View.VISIBLE
                holder.btnFlip.text = "Hide Answer"
            } else {
                holder.tvAnswer.visibility = View.GONE
                holder.btnFlip.text = "Show Answer"
            }
        }

        holder.btnDelete.setOnClickListener { onDelete(flashcard) }
    }

    class DiffCallback : DiffUtil.ItemCallback<Flashcard>() {
        override fun areItemsTheSame(oldItem: Flashcard, newItem: Flashcard) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Flashcard, newItem: Flashcard) =
            oldItem == newItem
    }
}