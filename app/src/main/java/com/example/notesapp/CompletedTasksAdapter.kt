package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CompletedTasksAdapter(private val completedNotes: List<Note>,private val deleteClickListener: OnDeleteClickListener) :
    RecyclerView.Adapter<CompletedTasksAdapter.CompletedTaskViewHolder>() {

    interface OnDeleteClickListener {
        fun onDeleteClicked(noteId: Int)
    }

    class CompletedTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val priorityTextView: TextView = itemView.findViewById(R.id.priorityTextView)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedTaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.completed_task_item, parent, false)
        return CompletedTaskViewHolder(view)
    }

    override fun getItemCount(): Int = completedNotes.size

    override fun onBindViewHolder(holder: CompletedTaskViewHolder, position: Int) {
        val note = completedNotes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
        holder.priorityTextView.text = note.priority.toString()

        holder.deleteButton.setOnClickListener {
            deleteClickListener.onDeleteClicked(note.id)
        }

    }
}