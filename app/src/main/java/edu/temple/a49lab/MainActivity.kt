package edu.temple.a49lab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val COUNTDOWN_SERVICE_ACTION = "countdownNumber"

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // assigns views
        editText = findViewById(R.id.editTextText)
        button = findViewById(R.id.button)

        // sets listener for button
        button.setOnClickListener {
            // gets text from edit text and it should be int
            val countdownNumberStr = editText.text.toString()
            if (countdownNumberStr.isNotEmpty()) {
                try {
                    //converts string to int
                    val countdownNumber = countdownNumberStr.toInt()
                    // starts service with countdown number
                    val serviceIntent = Intent(this, CountdownService::class.java)
                    // sends countdown number to service by using an intent
                    serviceIntent.putExtra(COUNTDOWN_SERVICE_ACTION, countdownNumber)
                        //begin the service
                    startService(serviceIntent)
                } catch (e: NumberFormatException) {
                    editText.error = "Please enter an integer"
                }

            } else {
                editText.error = "Please enter a number"
            }
        }
    }
}