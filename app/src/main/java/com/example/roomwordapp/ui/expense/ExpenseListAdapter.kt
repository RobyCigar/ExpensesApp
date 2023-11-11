package com.example.roomwordapp.ui.expense

import android.content.Context
import android.content.Intent
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordapp.MainApplication
import com.example.roomwordapp.R
import com.example.roomwordapp.data.entity.Expense
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExpenseListAdapter(private val context: Context) : ListAdapter<Expense, ExpenseListAdapter.WordViewHolder>(
    WordsComparator()
) {

    private var position: Int =  0
    private var actionMode: ActionMode? = null

    interface OnItemClickListener {
        fun onItemClick(data: Expense)
    }
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        this.position = holder.adapterPosition

        holder.bind(current)
        val item = currentList[position]


        holder.itemView.setOnLongClickListener { view: View ->

            if (actionMode == null) {
                actionMode = view.startActionMode(actionModeCallback)
                view.isSelected = true
            }
            true
        }

    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        private val description: TextView = itemView.findViewById(R.id.textDescription)
        private val amount: TextView = itemView.findViewById(R.id.textAmount)

        fun bind(expense: Expense) {
            wordItemView.text = expense.title
            description.text = expense?.description
            amount.text = expense?.amount.toString()
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.title == newItem.title
        }
    }


//    ACTION MODE
    private val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
            val inflater: MenuInflater = mode.menuInflater
            mode.title = "Aksi"
            inflater.inflate(R.menu.contextual_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
            val currItem = currentList[position]

            if(item.itemId == R.id.action_edit) {
                val intent = Intent(context, CreateExpenseActivity::class.java)
                intent.putExtra("word", item.toString())
                context.startActivity(intent)
                listener?.onItemClick(currItem)
            } else if(item.itemId == R.id.action_delete) {
                GlobalScope.launch {
                    currentList[position].id?.let { it1 -> MainApplication().database.expenseDao().deleteByUserId(id = it1) }
                }
            }
            return when (item.itemId) {
                R.id.action_edit ->                     // Handle the edit action
                    true

                R.id.action_delete ->                     // Handle the delete action
                    true

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }
    }
}