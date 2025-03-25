package com.example.assignment4_katietrinh_catherineyabut

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class TodoDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_todo_detail)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val todoId = intent.getStringExtra("todo_id")
        val btnDelete = findViewById<Button>(R.id.btn_delete)

        btnDelete.setOnClickListener {
            FirebaseFirestore.getInstance().collection("todos")
                .document(todoId!!)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Todo deleted!", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}