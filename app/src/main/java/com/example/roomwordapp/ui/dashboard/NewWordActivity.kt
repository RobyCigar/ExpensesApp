package com.example.roomwordapp.ui.dashboard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.roomwordapp.R
import com.example.roomwordapp.data.entity.Word
import com.example.roomwordapp.MainApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)
        val receivedData = intent.getStringExtra("word")

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val text = editWordView.text.toString()
            GlobalScope.launch {
                MainApplication().database.wordDao().insert(Word(word = text))
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
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}