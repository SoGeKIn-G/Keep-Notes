package com.gauravbora.mynotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gauravbora.mynotes.Modals.Note
import com.gauravbora.mynotes.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: Note
    private lateinit var oldNote: Note
    var isupdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText((oldNote.title))
            binding.etNote.setText(oldNote.note)
            isupdate = true

        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.check.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isupdate) {
                    note = Note(
                        oldNote.id, title, note_desc, formatter.format(Date())
                    )

                } else {
                    note = Note(
                        null, title, note_desc, formatter.format(Date())
                    )

                }

                val intent= Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            else{
                Toast.makeText(this@AddNote,"Please Enter Some Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }


        binding.backArrow.setOnClickListener{
            onBackPressed()
        }

    }
}