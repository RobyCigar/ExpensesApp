package com.example.roomwordapp.ui.dashboard

import android.content.Context
import android.content.Intent
import android.util.Log
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
import com.example.roomwordapp.data.entity.Word
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WordListAdapter(private val context: Context) : ListAdapter<Word, WordListAdapter.WordViewHolder>(
    WordsComparator()
) {

    private var actionMode: ActionMode? = null

    interface OnItemClickListener {
        fun onItemClick(data: Word)
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
        holder.bind(current.word)
        val item = currentList[position]


        holder.itemView.setOnLongClickListener { view: View ->

            if (actionMode == null) {
                actionMode = view.startActionMode(actionModeCallback)
                view.isSelected = true
            }
            true
        }

        // Handle edit button click
        holder.fab.setOnClickListener {
            val intent = Intent(context, NewWordActivity::class.java)
            intent.putExtra("word", item.toString())
            context.startActivity(intent)
            listener?.onItemClick(item)
        }

        holder.btnDelete.setOnClickListener {
            GlobalScope.launch {
                currentList[position].id?.let { it1 -> MainApplication().database.wordDao().deleteByUserId(id = it1) }
            }
        }

    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        val fab: View = itemView.findViewById(R.id.buttonEdit)
        val btnDelete: View = itemView.findViewById(R.id.buttonDelete)


        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
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