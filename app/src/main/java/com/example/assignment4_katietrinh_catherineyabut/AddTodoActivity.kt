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
            val userId = findViewById<EditText>(R.id.et_user_id).text.toString().toInt()
            val id = findViewById<EditText>(R.id.et_id).text.toString().toInt()
            val title = findViewById<EditText>(R.id.et_title).text.toString()

            val todo = Todo(userId, id, title)
            FirebaseFirestore.getInstance().collection("todos")
                .document(id.toString())
                .set(todo)
                .addOnSuccessListener {
                    Toast.makeText(this, "Todo added!", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}