package com.testingsite.mynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.testingsite.mynotes.R
import com.testingsite.mynotes.databinding.FragmentUpcomingAlarmsBinding

class UpcomingAlarmsFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingAlarmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpcomingAlarmsBinding.inflate(inflater, container, false)
        return binding.root
    }

}