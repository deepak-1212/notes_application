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

class NoteAdapter(val note: MutableList<Note>, applicationContext: Context?) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private lateinit var binding: SingleNoteBinding
    private var contextAdapter: Context? = applicationContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = SingleNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(note[position]) {
            binding.textView.text = title
            binding.textView2.text = message

            binding.singleNoteView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(
                    note[position],
                    "update"
                )

                Navigation.findNavController(it).navigate(action)
            }

            binding.menuImage.setOnClickListener {
                val popupMenu = PopupMenu(contextAdapter, it)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.addToArchive -> updateArchive(
                            note[position],
                            position
                        )
                    }
                    true
                }
                popupMenu.show()
            }

            /*binding.singleNoteView.setOnLongClickListener {
                Log.i("TAG", "onBindViewHolder: Test")
                true
            }*/
        }
    }

    private fun updateArchive(
        notes: Note,
        position: Int
    ) {

        /*val homeFragment = HomeFragment()
        homeFragment.updateList(notes, position, contextAdapter, note)*/

        val noteUpdated = Note(notes.title, notes.message, 1, "")

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            noteUpdated.id = notes.id
            NoteDatabase(this.contextAdapter!!).getNoteDao().updateNote(noteUpdated)
            handler.post {
                this.contextAdapter!!.toast("Added to Archives")
                //this.note.drop(position)
                this.note.removeAt(position)
                notifyItemRemoved(position)
                //notifyItemRangeChanged(position, this.note.size)
            }
        }
    }

    override fun getItemCount() = note.size

    inner class NoteViewHolder(binding: SingleNoteBinding) : RecyclerView.ViewHolder(binding.root)

}