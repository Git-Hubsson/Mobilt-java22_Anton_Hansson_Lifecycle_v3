package com.example.lifecycle

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SavedInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_info)
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        Welcome.ageValue = sharedPreferences.getString("Age", Welcome.ageValue)
        Welcome.isChecked = sharedPreferences.getBoolean("Ok", Welcome.isChecked)
        Welcome.ratingValue = sharedPreferences.getFloat("Rating", Welcome.ratingValue)
        Welcome.selectedOccupation = sharedPreferences.getString("Occupation", Welcome.selectedOccupation)
        editor.putBoolean("Ok", Welcome.isChecked)
        editor.putString("Rating", Welcome.ratingValue.toString())
        editor.putString("Occupation", Welcome.selectedOccupation)
        val isThePersonOk: String
        isThePersonOk = if (Welcome.isChecked) {
            "You are ok"
        } else {
            "You are not ok"
        }
        val answers = findViewById<EditText>(R.id.answers)
        answers.setText(
            """
    You are ${Welcome.ageValue}
    $isThePersonOk
    You gave this app a rating of ${Welcome.ratingValue}
    Your main occupation is ${Welcome.selectedOccupation}
    """.trimIndent()
        )
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.item3) {
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}