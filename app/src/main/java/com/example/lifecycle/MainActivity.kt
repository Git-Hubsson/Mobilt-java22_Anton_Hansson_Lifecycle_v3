package com.example.lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Log.d("Loginnn", "Innan sharedpref")

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val loginButton = findViewById<Button?>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        usernameInput = findViewById(R.id.username)
//        Log.d("Loginnn", "Innan usernameget settext")

        usernameInput.setText(sharedPreferences.getString("Age Interrupted", "asdf"))


//        Log.d("Loginnn", "Innan klickkod")
        loginButton?.setOnClickListener {
//            Log.d("Loginnn", "hej")
            val db = FirebaseFirestore.getInstance()
            val passwordInput = findViewById<EditText>(R.id.password)
            val inputUsername = usernameInput.text.toString()
            db.collection("users")
                .whereEqualTo("username", inputUsername)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {

                            val username = document.getString("username")
                            val password = document.getString("password")
                            if (password == passwordInput.text.toString() && username == usernameInput.text.toString()) {
                                loggedInUser = username
                                val i = Intent(this@MainActivity, Welcome::class.java)
                                startActivity(i)
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Password does not match username",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Something went wrong fetching data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        registerButton.setOnClickListener {
            val i = Intent(this, Register::class.java)
            startActivity(i)
        }
    }


    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val userValue = usernameInput.text.toString()
        editor.putString("Age Interrupted", userValue);
        editor.apply()
        Log.d("Age Interrupted", "Info sparad pga paus")
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val userValue = usernameInput.text.toString()
        editor.putString("Age Interrupted", userValue);
        editor.apply()
        Log.d("Age Interrupted", "Info sparad pga stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val userValue = usernameInput.text.toString()
        editor.putString("Age Interrupted", userValue);
        editor.apply()
        Log.d("Age Interrupted", "Info sparad pga destroy")
    }
    companion object {
        @JvmField
        var loggedInUser = ""
    }
}