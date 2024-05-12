package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        val searchBar = findViewById<SearchView>(R.id.searchBar)
        val sortButton = findViewById<ImageButton>(R.id.sortButton)

        sortButton.setOnClickListener {
            val sortedNotes = db.getAllNotesSort()
            notesAdapter.refreshData(sortedNotes)
        }

        searchBar.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    // Show all notes if the search bar is empty
                    notesAdapter.refreshData(db.getAllNotes())
                } else {
                    // Filter the notes by title and update the adapter
                    val filteredNotes = db.getAllNotes().filter { note -> note.title.contains(newText, ignoreCase = true) }
                    notesAdapter.refreshData(filteredNotes)
                }
                return true
            }
        })


        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        binding.completedTasksButton.setOnClickListener{
            val intent = Intent(this,CompletedNotesActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())


    }



}