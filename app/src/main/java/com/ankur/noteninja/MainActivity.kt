package com.ankur.noteninja

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ankur.noteninja.Adapter.NotesAdapter
import com.ankur.noteninja.Database.NoteDatabase
import com.ankur.noteninja.Database.NoteRepository
import com.ankur.noteninja.Models.Note
import com.ankur.noteninja.Models.NoteViewModel
import com.ankur.noteninja.Models.NoteViewModelFactory
import com.ankur.noteninja.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() ,NotesAdapter.NotesClickListener,PopupMenu.OnMenuItemClickListener{

    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Note



    val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->

        if (result.resultCode==Activity.RESULT_OK)
        {
            val note = result.data?.getSerializableExtra("note") as? Note

            if (note!=null)
            {
                mainViewModel.updateNote(note)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()


        val database = NoteDatabase.getDatabase(this).getNoteDao()
        val repository = NoteRepository(database)

        mainViewModel =
            ViewModelProvider(this, NoteViewModelFactory(repository))[NoteViewModel::class.java]

        mainViewModel.getAllNotes().observe(this) { list ->

            list?.let {
                adapter.updateList(list as ArrayList<Note>)
            }


        }
    }

    private fun updateUI() {

        binding.RecyclerView.setHasFixedSize(true)
        binding.RecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter=NotesAdapter(this,this)
        binding.RecyclerView.adapter=adapter


        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->

            if (result.resultCode==Activity.RESULT_OK)
            {
                val note = result.data?.getSerializableExtra("note") as? Note

                if (note!=null)
                {
                    mainViewModel.insert(note)
                }
            }
        }


        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this,AddNote::class.java)
            getContent.launch(intent)

        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!=null)
                {
                    adapter.filterList(newText)
                }

                return true
            }


        })


    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this@MainActivity,AddNote::class.java)
        intent.putExtra("Current_note",note)
        updateNote.launch(intent)

    }

    override fun onLongItemClick(note: Note, cardView: CardView) {
        selectedNote=note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {

        val popUp=PopupMenu(this,cardView)
        popUp.setOnMenuItemClickListener(this@MainActivity)
        popUp.inflate(R.menu.pop_up_menu)
        popUp.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        if (item?.itemId ==R.id.DeleteNote){
            mainViewModel.delete(selectedNote)
            return true}

        return false

    }

}


