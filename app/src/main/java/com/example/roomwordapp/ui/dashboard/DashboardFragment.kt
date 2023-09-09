package com.example.roomwordapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordapp.R
import com.example.roomwordapp.WordListAdapter
import com.example.roomwordapp.WordViewModel
import com.example.roomwordapp.WordViewModelFactory
import com.example.roomwordapp.WordsApplication
import com.example.roomwordapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val appCtx = activity
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val recyclerView = binding.recyclerview
        val adapter = context?.let { WordListAdapter(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        wordViewModel.allWords.observe(viewLifecycleOwner, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                adapter?.submitList(it)
            }
        })


        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((activity?.application as WordsApplication).repository)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}