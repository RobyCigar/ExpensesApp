package com.example.roomwordapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)
        val receivedData = intent.getStringExtra("word")

        Log.d("DATA", receivedData.toString())
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val text = editWordView.text.toString()
            GlobalScope.launch {
                WordsApplication().database.wordDao().insert(Word(word = text))
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