package com.example.lifecycle

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("UseSwitchCompatOrMaterialCode")
class Welcome : AppCompatActivity() {

    var ageFragment: AgeFragment? = null
    var okFragment: OkFragment? = null
    var ratingFragment: RatingFragment? = null
    var spinner: Spinner? = null
    var switchEnableForm: Switch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        ageFragment = AgeFragment()
        okFragment = OkFragment()
        ratingFragment = RatingFragment()
        spinner = findViewById(R.id.spinner)

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        welcomeText.text = "Welcome " + MainActivity.loggedInUser

        val spinnerItems = resources.getStringArray(R.array.spinnerItems)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.setAdapter(adapter)
        spinner?.setSelection(0, false)

        val fm = supportFragmentManager
        fm.beginTransaction().add(R.id.fragmentContainer, ageFragment!!)
            .add(R.id.fragmentContainer, okFragment!!)
            .add(R.id.fragmentContainer, ratingFragment!!)
            .commit()

        val saveInfo = findViewById<Button>(R.id.saveInfo)
        saveInfo.isEnabled = false
        switchEnableForm = findViewById(R.id.switch1)
        switchEnableForm?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            saveInfo.isEnabled = isChecked
        })

        saveInfo.setOnClickListener {
            val ageEdit = ageFragment!!.requireView().findViewById<EditText>(R.id.age)
            ageValue = ageEdit.text.toString()
            val checkBox = okFragment!!.requireView().findViewById<CheckBox>(R.id.areYouOk)
            isChecked = checkBox.isChecked
            val ratingBar = ratingFragment!!.requireView().findViewById<RatingBar>(R.id.ratingBar)
            ratingValue = ratingBar.rating
            selectedOccupation = spinner?.getSelectedItem().toString()
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("Age", ageValue)
            editor.putBoolean("Ok", isChecked)
            editor.putFloat("Rating", ratingValue)
            editor.putString("Occupation", selectedOccupation)
            editor.apply()
            val i = Intent(this@Welcome, SavedInfo::class.java)
            startActivity(i)
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val ageEdit = ageFragment!!.requireView().findViewById<EditText>(R.id.age)
        editor.putString("Age Interrupted", ageEdit.text.toString());
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val ageEdit = ageFragment!!.requireView().findViewById<EditText>(R.id.age)
        editor.putString("Age Interrupted", ageEdit.text.toString());
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val ageEdit = ageFragment!!.requireView().findViewById<EditText>(R.id.age)
        editor.putString("Age Interrupted", ageEdit.text.toString());
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.item2) {
            val i = Intent(this@Welcome, SavedInfo::class.java)
            startActivity(i)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var ageValue: String? = null
        var isChecked = false
        var ratingValue = 0f
        var selectedOccupation: String? = null
    }
}