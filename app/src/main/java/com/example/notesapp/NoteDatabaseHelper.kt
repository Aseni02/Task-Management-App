package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 5
        private const val TABLE_NAME = "allnotes"
        private const val TABLE_COMPLETED_NOTES = "completednotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_COMPLETED = "completed"
        private const val COLUMN_PRIORITY = "priority"

    }

    //    override fun onCreate(db: SQLiteDatabase?) {
//        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT,$COLUMN_COMPLETED INTEGER DEFAULT 0,$COLUMN_PRIORITY INTEGER DEFAULT 0)"
//        db?.execSQL(createTableQuery)
//
//        val createCompletedTableQuery = "CREATE TABLE $TABLE_COMPLETED_NOTES ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT,$COLUMN_PRIORITY INTEGER DEFAULT 0)"
//        db?.execSQL(createCompletedTableQuery)
//    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT,$COLUMN_COMPLETED INTEGER DEFAULT 0,$COLUMN_PRIORITY INTEGER DEFAULT 0)"
        db?.execSQL(createTableQuery)

        val createCompletedTableQuery =
            "CREATE TABLE IF NOT EXISTS $TABLE_COMPLETED_NOTES ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT,$COLUMN_PRIORITY INTEGER DEFAULT 0)"
        db?.execSQL(createCompletedTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)

        val dropCompletedTableQuery = "DROP TABLE IF EXISTS $TABLE_COMPLETED_NOTES"
        db?.execSQL(dropCompletedTableQuery)
        onCreate(db)


    }

    fun insertNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_PRIORITY, note.priority)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    //    fun getAllNotes(): List<Note>{
//        val notesList = mutableListOf<Note>()
//        val db = readableDatabase
//        val query = "SELECT * FROM $TABLE_NAME"
//        val cursor = db.rawQuery(query, null)
//
//        while(cursor.moveToNext()){
//            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
//            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
//            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
//            val priority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
//
//            val note = Note(id, title, content,priority)
//            notesList.add(note)
//
//        }
//        cursor.close()
//        db.close()
//        return notesList
//
//
//
//    }
    fun getAllNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        readableDatabase.use { db ->
            val query = "SELECT * FROM $TABLE_NAME"
            val cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
                val priority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))

                val note = Note(id, title, content, priority)
                notesList.add(note)
            }
            cursor.close()
        }
        return notesList
    }
    fun getAllNotesSort(): List<Note> {
        val notesList = mutableListOf<Note>()
        readableDatabase.use { db ->
            val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_PRIORITY ASC"
            val cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
                val priority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))

                val note = Note(id, title, content, priority)
                notesList.add(note)
            }
            cursor.close()
        }
        return notesList
    }


    fun updateNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_PRIORITY, note.priority)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()

    }

    fun getNoteByID(noteId: Int): Note {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val priority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))

        cursor.close()
        db.close()
        return Note(id, title, content, priority)


    }

    fun deleteNote(noteId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()

    }
    fun deleteNote2(noteId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_COMPLETED_NOTES, whereClause, whereArgs)
        db.close()
    }

//    fun markNoteAsCompleted(noteId: Int) {
//        val db = writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_COMPLETED, 1)
//        }
//        val whereClause = "$COLUMN_ID =?"
//        val whereArgs = arrayOf(noteId.toString())
//        db.update(TABLE_NAME, values, whereClause, whereArgs)
//        db.close()
//    }

    fun markNoteAsCompleted(noteId: Int) {
        val db = writableDatabase
        val note = getNoteByID(noteId)
        moveNoteToCompletedNotes(note)
        deleteNote(noteId)
    }

    private fun moveNoteToCompletedNotes(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_PRIORITY, note.priority)
        }
        db.insert(TABLE_COMPLETED_NOTES, null, values)
        db.close()
    }

    fun getAllCompletedNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        readableDatabase.use { db ->
            val query = "SELECT * FROM $TABLE_COMPLETED_NOTES"
            val cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
                val priority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))

                val note = Note(id, title, content, priority, true)
                notesList.add(note)
            }
            cursor.close()
        }
        return notesList
    }


}