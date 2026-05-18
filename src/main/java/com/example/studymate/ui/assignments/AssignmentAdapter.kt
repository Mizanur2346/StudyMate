package com.example.studymate.ui.assignments

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.data.model.Assignment

class AssignmentAdapter(
    private val onDelete: (Assignment) -> Unit,
    private val onComplete: (Assignment) -> Unit
) : ListAdapter<Assignment, AssignmentAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvSubject: TextView = view.findViewById(R.id.tvSubject)
        val tvDueDate: TextView = view.findViewById(R.id.tvDueDate)
        val tvPriority: TextView = view.findViewById(R.id.tvPriority)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
        val btnComplete: Button = view.findViewById(R.id.btnComplete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_assignment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val assignment = getItem(position)
        holder.tvTitle.text = assignment.title
        holder.tvSubject.text = assignment.subject
        holder.tvDueDate.text = "Due: ${assignment.dueDate}"
        holder.tvPriority.text = "Priority: ${assignment.priority}"

        if (assignment.isCompleted) {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.btnComplete.text = "Undo"
        } else {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.btnComplete.text = "Done"
        }

        holder.btnDelete.setOnClickListener { onDelete(assignment) }
        holder.btnComplete.setOnClickListener { onComplete(assignment) }
    }

    class DiffCallback : DiffUtil.ItemCallback<Assignment>() {
        override fun areItemsTheSame(oldItem: Assignment, newItem: Assignment) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Assignment, newItem: Assignment) =
            oldItem == newItem
    }
}