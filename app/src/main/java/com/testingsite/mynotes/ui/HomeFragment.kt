package com.testingsite.mynotes.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.testingsite.mynotes.R
import com.testingsite.mynotes.databinding.FragmentHomeBinding
import com.testingsite.mynotes.db.Note
import com.testingsite.mynotes.db.NoteDatabase
import com.testingsite.mynotes.utils.toast
import java.util.concurrent.Executors

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding = FragmentHomeBinding.bind(view)
        val orientation = activity?.resources?.configuration?.orientation

        binding.recyclerViewNotes.setHasFixedSize(true)
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.recyclerViewNotes.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        else
            binding.recyclerViewNotes.layoutManager =
                StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val notes = NoteDatabase(requireActivity()).getNoteDao().getNote()
            handler.post {
                binding.recyclerViewNotes.adapter = NoteAdapter(notes, activity?.applicationContext)
            }
        }

        binding.buttonAdd.setOnClickListener {
            Log.i("TAG", "onViewCreated: Button Clicked")
            val note = Note("", "", 0, "")
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(note, "insert")

            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.view_archive, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.archive_list -> checkArchiveList()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkArchiveList() {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val notes = NoteDatabase(requireActivity()).getNoteDao().getArchivedNote()
            handler.post {
                if (notes.isNotEmpty()) {
                    val action = HomeFragmentDirections.actionHomeFragmentToArchiveFragment()

                    Navigation.findNavController(binding.recyclerViewNotes).navigate(action)
                } else
                    context?.toast("No Archives Stored")
            }
        }
    }

    /*override fun updateList(note: Note, position: Int, contextAdapter: Context?, notes: List<Note>) {

        val noteUpdated = Note(note.title, note.message, 1)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            noteUpdated.id = note.id
            NoteDatabase(contextAdapter!!).getNoteDao().updateNote(noteUpdated)
            handler.post {
                contextAdapter!!.toast("Added to Archives")
                notes.drop(position)
                binding.recyclerViewNotes.adapter?.notifyItemRemoved(position)
                binding.recyclerViewNotes.adapter?.notifyItemRangeChanged(position, notes.size)
            }
        }
    }*/

}