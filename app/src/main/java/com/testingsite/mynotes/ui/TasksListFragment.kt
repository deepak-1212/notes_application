package com.testingsite.mynotes.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.testingsite.mynotes.R
import com.testingsite.mynotes.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment() {
    private lateinit var binding: FragmentTasksListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setHasOptionsMenu(true)

    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.addTaskItem -> {
                val action = TasksListFragmentDirections.actionSaveTask()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }

        return super.onOptionsItemSelected(item)
    }*/

}