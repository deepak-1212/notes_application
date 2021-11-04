package com.testingsite.mynotes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.testingsite.mynotes.R
import com.testingsite.mynotes.databinding.FragmentAddNoteBinding
import com.testingsite.mynotes.db.Note
import com.testingsite.mynotes.db.NoteDatabase
import com.testingsite.mynotes.utils.hideKeyboard
import com.testingsite.mynotes.utils.toast
import java.util.concurrent.Executors

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    private lateinit var binding: FragmentAddNoteBinding

    val args: AddNoteFragmentArgs by navArgs()
    private var mnote: Note? = null
    private lateinit var clickFrom: String

    var title = ""
    var body = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //binding = FragmentAddNoteBinding.bind(view)
        mnote = args.Note
        clickFrom = args.ClickFrom


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.deleteItem -> if (clickFrom.equals("update")) deleteNote() else context?.toast("Cannot Delete")
            R.id.archiveItem -> if (clickFrom.equals("update")) archiveNote() else context?.toast("Please save first")
        }

        return super.onOptionsItemSelected(item)
    }

    private fun archiveNote() {

        if (checkEditValue()) {

            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            executor.execute {

                val archivedSelectedNote =
                    NoteDatabase(requireContext()).getNoteDao().getQueriedArchivedNote(mnote!!.id)

                if (archivedSelectedNote.size == 0) {
                    val noteUpdated = Note(title, body, 1, "")

                    noteUpdated.id = mnote!!.id
                    NoteDatabase(requireContext()).getNoteDao().updateNote(noteUpdated)
                    handler.post {
                        context?.toast("Added to Archives")
                    }
                } else {
                    val noteUpdated = Note(title, body, 0, "")

                    noteUpdated.id = mnote!!.id
                    NoteDatabase(requireContext()).getNoteDao().updateNote(noteUpdated)
                    handler.post {
                        context?.toast("Removed from Archives")
                    }
                }

            }
        }

    }

    private fun checkEditValue(): Boolean {
        title = binding.title.text.toString().trim()
        body = binding.body.text.toString().trim()

        if (title.isEmpty()) {
            binding.title.error = "Field cannot be blank"
            binding.title.requestFocus()
            return false
        }

        if (body.isEmpty()) {
            binding.body.error = "Field cannot be blank"
            binding.body.requestFocus()
            return false
        }

        return true
    }

    private fun deleteNote() {
        context?.hideKeyboard(binding.title)
        context?.hideKeyboard(binding.body)
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ ->
                val executor = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())
                executor.execute {
                    NoteDatabase(requireActivity()).getNoteDao().deleteNote(mnote!!)
                    handler.post {
                        context.toast("Note Deleted")
                        val action = AddNoteFragmentDirections.actionSaveNote()
                        Navigation.findNavController(binding.root).navigate(action)
                    }
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (clickFrom.equals("update")) {
            //activity?.actionBar?.title = "Update Note"
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Update Note"

            setHasOptionsMenu(true)
        }

        if (mnote != null) {
            binding.title.setText(mnote?.title)
            binding.body.setText(mnote?.message)
        }


        binding.floatingActionButton.setOnClickListener() {
            context?.hideKeyboard(binding.title)
            context?.hideKeyboard(binding.body)

            if (checkEditValue()) {
                val note = Note(title, body, 0, "")

                val executor = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())
                executor.execute {

                    if (clickFrom.equals("update")) {
                        note.id = mnote!!.id
                        NoteDatabase(requireActivity()).getNoteDao().updateNote(note)
                    } else if (clickFrom.equals("insert")) {
                        NoteDatabase(requireActivity()).getNoteDao().insertNote(note)
                    }

                    handler.post {
                        val action = AddNoteFragmentDirections.actionSaveNote()
                        Navigation.findNavController(it).navigate(action)

                    }
                }
            }

        }

    }

}