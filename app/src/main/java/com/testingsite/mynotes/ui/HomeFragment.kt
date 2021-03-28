package com.testingsite.mynotes.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.testingsite.mynotes.R
import com.testingsite.mynotes.databinding.FragmentHomeBinding
import com.testingsite.mynotes.db.Note
import com.testingsite.mynotes.db.NoteDatabase
import java.util.concurrent.Executors

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        binding.recyclerViewNotes.setHasFixedSize(true)
        binding.recyclerViewNotes.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val notes = NoteDatabase(requireActivity()).getNoteDao().getNote()
            handler.post {
                binding.recyclerViewNotes.adapter = NoteAdapter(notes)


             }
        }

        binding.buttonAdd.setOnClickListener {
            Log.i("TAG", "onViewCreated: Button Clicked")
            val note = Note("", "")
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(note, "insert")

            Navigation.findNavController(view).navigate(action)
        }
    }


}