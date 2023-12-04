package com.example.roomwordapp.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.roomwordapp.MainApplication
import com.example.roomwordapp.R
import com.example.roomwordapp.data.entity.Category
import com.example.roomwordapp.ui.home.HomeFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        val name  = findViewById<EditText>(R.id.categoryName)
        val description  = findViewById<EditText>(R.id.categoryDescription)
        val button  = findViewById<Button>(R.id.categorySave)
        button.setOnClickListener {
            GlobalScope.launch {
                val category: Category = Category(
                    name = name.text.toString(),
                    id = null
                )

                MainApplication().database.categoryDao().insert(category)
            }
            Intent(this, HomeFragment::class.java)
        }
    }
}