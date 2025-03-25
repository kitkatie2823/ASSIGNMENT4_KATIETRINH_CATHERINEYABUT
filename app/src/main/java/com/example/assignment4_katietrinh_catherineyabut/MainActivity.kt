package com.example.assignment4_katietrinh_catherineyabut

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Firebase.firestore
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_api -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ApiFragment()).commit()
                    true
                }
                R.id.nav_display -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DisplayFragment()).commit()
                    true
                }
                else -> false
            }
        }
        // Load default fragment
        bottomNav.selectedItemId = R.id.nav_api
    }
}