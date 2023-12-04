package com.example.roomwordapp.ui.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomwordapp.MainApplication
import com.example.roomwordapp.data.viewmodel.ExpenseViewModel
import com.example.roomwordapp.data.viewmodel.ExpenseViewModelFactory
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
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val recyclerView = binding.recyclerview
        val adapter = context?.let { ExpenseListAdapter(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        expenseViewModel.allWords.observe(viewLifecycleOwner, Observer { words ->
            words?.let {
                adapter?.submitList(it)
            }
        })

        return root
    }
    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((activity?.application as MainApplication).repository)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}