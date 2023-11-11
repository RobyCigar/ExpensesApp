package com.example.roomwordapp.ui.expense

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.roomwordapp.R
import com.example.roomwordapp.data.entity.Expense
import com.example.roomwordapp.MainApplication
import com.example.roomwordapp.databinding.ActivityNewWordBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var binding: ActivityNewWordBinding


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)
        val receivedData = intent.getStringExtra("word")
        val description = findViewById<TextInputEditText>(R.id.inputDescription)
        val amount = findViewById<TextInputEditText>(R.id.inputAmount)
        val button = findViewById<Button>(R.id.button_save)

        button.setOnClickListener {
            val word = Expense(title = editWordView.text.toString(), description = description.text.toString(), amount = amount.text.toString().toInt())
            GlobalScope.launch {
                MainApplication().database.expenseDao().insert(word)
            }
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.autoCompleteTextView.setAdapter(adapter)
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}