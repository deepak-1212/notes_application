package com.testingsite.mynotes.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.testingsite.mynotes.databinding.SingleNoteBinding
import com.testingsite.mynotes.db.Note
import com.testingsite.mynotes.db.NoteDatabase
import com.testingsite.mynotes.ui.HomeFragmentDirections
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

            /*binding.menuImage.setOnClickListener {
                val popupMenu = PopupMenu(contextAdapter, it)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.addToArchive -> updateArchive(
                            note[position],
                            position
                        )
                        R.id.delete -> contextAdapter?.toast("Delete Clicked") *//*deleteNote(

                            note[position],
                            position,
                            binding.root
                        )*//*
                    }
                    true
                }
                popupMenu.show()
            }*/

            /*binding.singleNoteView.setOnLongClickListener {
                Log.i("TAG", "onBindViewHolder: Test")
                true
            }*/
        }
    }

    /*private fun deleteNote(notes: Note, position: Int, root: View) {
        AlertDialog.Builder(contextAdapter).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ ->
                val executor = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())
                executor.execute {
                    contextAdapter?.let { NoteDatabase(it).getNoteDao().deleteNote(notes) }
                    handler.post {
                        context.toast("Note Deleted")

                        note.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }*/

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
                contextAdapter!!.toast("Added to Archives")
                //this.note.drop(position)
                note.removeAt(position)
                notifyItemRemoved(position)
                //notifyItemRangeChanged(position, this.note.size)
            }
        }
    }

    override fun getItemCount() = note.size

    inner class NoteViewHolder(binding: SingleNoteBinding) : RecyclerView.ViewHolder(binding.root)

}