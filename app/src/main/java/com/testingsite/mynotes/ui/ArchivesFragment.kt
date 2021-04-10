package com.testingsite.mynotes.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.testingsite.mynotes.databinding.FragmentArchivesBinding
import com.testingsite.mynotes.db.NoteDatabase
import java.util.concurrent.Executors

class ArchivesFragment : Fragment() {

    private lateinit var binding: FragmentArchivesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentArchivesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val orientation = activity?.resources?.configuration?.orientation

        //binding = FragmentArchivesBinding.bind(view)

        binding.recyclerArchivedNotes.setHasFixedSize(true)
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.recyclerArchivedNotes.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        else
            binding.recyclerArchivedNotes.layoutManager =
                StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val notes = NoteDatabase(requireActivity()).getNoteDao().getArchivedNote()
            handler.post {
                binding.recyclerArchivedNotes.adapter =
                    NoteArchiveAdapter(notes, activity?.applicationContext)
            }
        }
    }

}