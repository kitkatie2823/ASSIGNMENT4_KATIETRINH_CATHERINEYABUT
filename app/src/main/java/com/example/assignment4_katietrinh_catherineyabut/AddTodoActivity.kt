package com.example.assignment4_katietrinh_catherineyabut

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment4_katietrinh_catherineyabut.model.Todo
import com.google.firebase.firestore.FirebaseFirestore

class AddTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_todo)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val btnSave = findViewById<Button>(R.id.btn_save)
        btnSave.setOnClickListener {
            val userId = findViewById<EditText>(R.id.et_user_id).text.toString().toString()
            val id = findViewById<EditText>(R.id.et_id).text.toString().toString()
            val title = findViewById<EditText>(R.id.et_title).text.toString()

            if (userId.isEmpty() || id.isEmpty() || title.isEmpty()) {
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val userId = userId.toInt()
                val id = id.toInt()
                // Proceed to save to Firestore
                val db = FirebaseFirestore.getInstance()
                val todo = Todo(userId, id, title, completed = false)
                db.collection("todos")
                    .document(id.toString()) // Use a unique ID (e.g., todo.id)
                    .set(todo)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Todo saved!", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid ID format!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}