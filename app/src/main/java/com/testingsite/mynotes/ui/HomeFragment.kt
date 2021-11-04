package com.testingsite.mynotes.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.testingsite.mynotes.R
import com.testingsite.mynotes.adapter.NoteAdapter
import com.testingsite.mynotes.databinding.FragmentHomeBinding
import com.testingsite.mynotes.db.Days
import com.testingsite.mynotes.db.Note
import com.testingsite.mynotes.db.NoteDatabase
import com.testingsite.mynotes.utils.AnimationUtils
import com.testingsite.mynotes.utils.toast
import java.text.FieldPosition
import java.util.concurrent.Executors

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private var rotate = false
    private lateinit var rotateView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        insertDaysIfEmpty();

        //binding = FragmentHomeBinding.bind(view)
        val orientation = activity?.resources?.configuration?.orientation

        AnimationUtils.init(binding.fabNote)
        AnimationUtils.init(binding.fabReminder)
        AnimationUtils.init(binding.tvNote)
        AnimationUtils.init(binding.tvReminder)

        binding.recyclerViewNotes.setHasFixedSize(true)
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.recyclerViewNotes.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        else
            binding.recyclerViewNotes.layoutManager =
                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val notes = NoteDatabase(requireActivity()).getNoteDao().getNote()

            if (notes.size == 0)
                binding.noNotesTv.visibility = View.VISIBLE
            else
                binding.noNotesTv.visibility = View.INVISIBLE

            handler.post {
                binding.recyclerViewNotes.adapter = NoteAdapter(notes, activity?.applicationContext)
            }
        }

        binding.buttonAdd.setOnClickListener {


            rotateView = it
            rotate = AnimationUtils.rotateFab(it, !rotate)

            if (rotate) {
                AnimationUtils.showIn(binding.fabNote);
                AnimationUtils.showIn(binding.fabReminder);
                AnimationUtils.showIn(binding.tvNote);
                AnimationUtils.showIn(binding.tvReminder);
            } else {
                AnimationUtils.showOut(binding.fabNote);
                AnimationUtils.showOut(binding.fabReminder);
                AnimationUtils.showOut(binding.tvNote);
                AnimationUtils.showOut(binding.tvReminder);
            }

        }

        binding.fabNote.setOnClickListener {

            rotate = false

            Log.i("TAG", "onViewCreated: Note Button Clicked")
            val note = Note("", "", 0, "")
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(note, "insert")
            Navigation.findNavController(view).navigate(action)

        }

        binding.fabReminder.setOnClickListener {

            rotate = false

            Log.i("TAG", "onViewCreated: $rotate")

            val action = HomeFragmentDirections.actionHomeFragmentToReminderFragment()
            Navigation.findNavController(view).navigate(action)

            /*context?.snackbar( LayoutInflater.from(context).inflate(R.layout.snackbar_view, binding.root, true), "Under Progress!")


            rotate = AnimationUtils.rotateFab(rotateView, !rotate)

            AnimationUtils.showOut(binding.fabNote)
            AnimationUtils.showOut(binding.fabReminder)
            AnimationUtils.showOut(binding.tvNote)
            AnimationUtils.showOut(binding.tvReminder)*/
        }
    }

    private fun insertDaysIfEmpty() {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val daysCount = NoteDatabase(requireActivity()).getNoteDao().getDaysCount()
            handler.post {
                if (daysCount == 0) {

                    insertDays("Sunday", 0)
                    insertDays("Monday", 1)
                    insertDays("Tuesday", 2)
                    insertDays("Wednesday", 3)
                    insertDays("Thursday", 4)
                    insertDays("Friday", 5)
                    insertDays("Saturday", 6)

                } else
                    Log.i("TAG", "insertDaysIfEmpty: days already inserted")
            }
        }

    }

    private fun insertDays(day: String, position: Int) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            NoteDatabase(requireActivity()).getNoteDao().insertDay(Days(day, position))
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
                    context?.toast("No Archives")
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