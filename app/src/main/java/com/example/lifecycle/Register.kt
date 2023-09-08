package com.example.lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val username = findViewById<EditText>(R.id.rUsername)
        val password = findViewById<EditText>(R.id.rPassword)
        val email = findViewById<EditText>(R.id.rEmail)
        val phoneNumber = findViewById<EditText>(R.id.rPhoneNumber)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val user: MutableMap<String, Any> = HashMap()
            user["username"] = username.text.toString()
            user["password"] = password.text.toString()
            user["email"] = email.text.toString()
            user["phone number"] = phoneNumber.text.toString()
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "Anton",
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                }
                .addOnFailureListener { e -> Log.w("Anton", "Error adding document", e) }
            val i = Intent(this@Register, MainActivity::class.java)
            startActivity(i)
        }
    }
}