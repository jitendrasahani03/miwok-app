package com.example.miwok

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PhrasesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.category_container,PhrasesFragment())
            .commit()
    }
}



