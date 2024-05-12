package com.example.notesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ActivityCompletedNotesBinding

class CompletedNotesActivity : AppCompatActivity(),CompletedTasksAdapter.OnDeleteClickListener {
    private lateinit var binding: ActivityCompletedNotesBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var completedTasksAdapter: CompletedTasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompletedNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.completedTasksRecyclerView.layoutManager = LinearLayoutManager(this)
        db = NoteDatabaseHelper(this)
        completedTasksAdapter = CompletedTasksAdapter(db.getAllCompletedNotes(),this)
        binding.completedTasksRecyclerView.adapter = completedTasksAdapter

        ViewCompat.setOnApplyWindowInsetsListener(binding.completedTasksRecyclerView) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            view.updateLayoutParams<RecyclerView.LayoutParams> {
                bottomMargin = bottom
            }
            insets
        }
    }
    override fun onDeleteClicked(noteId: Int) {
        db.deleteNote2(noteId)
        refreshAdapter()
        Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show()
    }
    private fun refreshAdapter() {
        completedTasksAdapter = CompletedTasksAdapter(db.getAllCompletedNotes(), this)
        binding.completedTasksRecyclerView.adapter = completedTasksAdapter
        completedTasksAdapter.notifyDataSetChanged()
    }
}