package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString().trim()
            val content = binding.contentEditText.text.toString().trim()
            val priorityText = binding.priorityEditText.text.toString().trim()

            // Check if any of the fields are empty
            if (title.isEmpty() || priorityText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // No need to parse priorityText to Int if it's already validated in XML

            val priority = priorityText.toInt()

            val note = Note(0, title, content, priority)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Task Saved ", Toast.LENGTH_SHORT).show()
        }




    }
}