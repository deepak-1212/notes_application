package com.testingsite.mynotes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.testingsite.mynotes.databinding.SingleNoteBinding
import com.testingsite.mynotes.db.Note

class NoteAdapter(val note: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private lateinit var binding: SingleNoteBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = SingleNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(note[position]) {
            binding.textView.text = title
            binding.textView2.text = message

            binding.singleNoteView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(note[position], "update")

                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = note.size

    inner class NoteViewHolder(binding: SingleNoteBinding) : RecyclerView.ViewHolder(binding.root)

}