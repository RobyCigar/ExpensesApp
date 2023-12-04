package com.example.roomwordapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordapp.MainApplication
import com.example.roomwordapp.data.viewmodel.ExpenseViewModel
import com.example.roomwordapp.data.viewmodel.ExpenseViewModelFactory
import com.example.roomwordapp.databinding.FragmentHomeBinding
import com.example.roomwordapp.ui.category.CreateCategoryActivity
import com.example.roomwordapp.ui.expense.CreateExpenseActivity
import com.example.roomwordapp.ui.expense.ExpenseListAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val context: Context = requireContext()

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val cardList = generateCardData()
        val adapter = CardAdapter(cardList)
        recyclerView.adapter = adapter

        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
        }

        val recyclerViewVertical = binding.rvExpenses
        val adapterVertical = context?.let { ExpenseListAdapter(it) }
        recyclerViewVertical.adapter = adapterVertical
        recyclerViewVertical.layoutManager = LinearLayoutManager(context)


        expenseViewModel.allWords.observe(viewLifecycleOwner, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                adapterVertical?.submitList(it)
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

    private fun generateCardData(): List<CardData> {
        val cardList = mutableListOf<CardData>()
        cardList.add(CardData("Pengeluaran", "Kelola data pengeluaran", destination = Intent(context, CreateExpenseActivity::class.java)))
        cardList.add(CardData("Pemasukan", "Kelola data pemasukan", destination = Intent(context, CreateExpenseActivity::class.java)))
        cardList.add(CardData("Kategori", "Kelola kategori transaksi", destination = Intent(context, CreateCategoryActivity::class.java)))
        // Add more cards as needed
        return cardList
    }
}