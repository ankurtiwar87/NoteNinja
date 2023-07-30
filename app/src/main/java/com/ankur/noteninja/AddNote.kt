package com.ankur.noteninja

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ankur.noteninja.Models.Note
import com.ankur.noteninja.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var oldNote:Note
    private lateinit var note: Note
    var isUpdate=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


            try {

                oldNote=intent.getSerializableExtra("Current_note")as Note
                binding.Title.setText(oldNote.title)
                binding.Notes.setText(oldNote.note)
                isUpdate=true
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }



        binding.imgCheck.setOnClickListener {
            val title = binding.Title.text.toString()
            val noteDes = binding.Notes.text.toString()

            if (title.isNotEmpty()|| noteDes.isNotEmpty())
            {

                val formatter =SimpleDateFormat("EEE,d MMM YYYY HH:mm a")

                if (isUpdate)
                {
                    note = Note(oldNote.id,title,noteDes,formatter.format(Date()))


                }
                else
                {
                    note = Note(null ,title,noteDes,formatter.format(Date()))
                }

                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()


            }
            
            else
            {
                Toast.makeText(this, "Please Enter Data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

        binding.imhBack.setOnClickListener {
            onBackPressed()
        }
    }
}