package com.example.roomwordapp.ui.expense

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
import kotlin.random.Random

class CreateExpenseActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    val channelId = generateRandomString(10)

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
            showNotification()
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
        createNotificationChannel()

    }
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory((this.application as MainApplication).categoryRepository)
    }
    private fun showNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Expenses berhasil dibuat!")
            .setContentText("This is the notification message.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notificationBuilder.build())
    }
    private fun generateRandomString(length: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') // Define the character pool
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel Name"
            val descriptionText = "My Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}