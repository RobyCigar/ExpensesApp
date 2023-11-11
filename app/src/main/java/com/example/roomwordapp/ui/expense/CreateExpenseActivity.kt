package com.example.roomwordapp.ui.expense

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.roomwordapp.R
import com.example.roomwordapp.data.entity.Expense
import com.example.roomwordapp.MainApplication
import com.example.roomwordapp.data.entity.Category
import com.example.roomwordapp.data.viewmodel.CategoryViewModel
import com.example.roomwordapp.data.viewmodel.CategoryViewModelFactory
import com.example.roomwordapp.data.viewmodel.ExpenseViewModel
import com.example.roomwordapp.data.viewmodel.ExpenseViewModelFactory
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateExpenseActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_expense)
        editWordView = findViewById(R.id.edit_word)
        val description = findViewById<TextInputEditText>(R.id.inputDescription)
        val amount = findViewById<TextInputEditText>(R.id.inputAmount)
        val button = findViewById<Button>(R.id.button_save)
        val dropdown = findViewById<MaterialAutoCompleteTextView>(R.id.dropdownTextView)

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
        categoryViewModel.allCategories.observe(this, Observer { categories ->
            // Update the cached copy of the words in the adapter.
            val namesArray = categories.map { it.name }.toTypedArray()

            dropdown.setSimpleItems(namesArray)
            categories?.let {
                Log.d("Category", it.toString())
            }
        })
    }
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory((this.application as MainApplication).categoryRepository)
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}