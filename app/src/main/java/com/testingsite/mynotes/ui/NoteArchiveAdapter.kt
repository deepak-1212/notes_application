package com.testingsite.mynotes.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.testingsite.mynotes.R
import com.testingsite.mynotes.databinding.SingleNoteBinding
import com.testingsite.mynotes.db.Note
import com.testingsite.mynotes.db.NoteDatabase
import com.testingsite.mynotes.utils.toast
import java.util.concurrent.Executors

class NoteArchiveAdapter(val note: MutableList<Note>, applicationContext: Context?) : RecyclerView.Adapter<NoteArchiveAdapter.NoteArchiveViewModel>() {
    private lateinit var binding: SingleNoteBinding
    private var contextAdapter: Context? = applicationContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteArchiveViewModel {
        binding = SingleNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteArchiveViewModel(binding)
    }

    override fun onBindViewHolder(holder: NoteArchiveViewModel, position: Int) {
        with(note[position]) {
            binding.textView.text = title
            binding.textView2.text = message

            binding.singleNoteView.setOnClickListener {
                val action = ArchivesFragmentDirections.actionArchiveFragmentToAddNoteFragment(note[position], "update")
                Navigation.findNavController(it).navigate(action)
            }

            binding.menuImage.setOnClickListener {
                val popupMenu = PopupMenu(contextAdapter, it)
                popupMenu.menuInflater.inflate(R.menu.popup_menu_remove, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.removeArchive -> updateArchive(note[position], position)
                    }
                    true
                }
                popupMenu.show()
            }
        }


    }

    private fun updateArchive(note: Note, position: Int) {
        val noteUpdated = Note(note.title, note.message, 0, "")

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            noteUpdated.id = note.id
            NoteDatabase(contextAdapter!!).getNoteDao().updateNote(noteUpdated)
            handler.post {
                contextAdapter!!.toast("Added to Archives")
                this.note.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount() = note.size

    inner class NoteArchiveViewModel(binding: SingleNoteBinding) : RecyclerView.ViewHolder(binding.root)
}