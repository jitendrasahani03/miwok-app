package com.example.miwok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ColorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.category_container,ColorsFragment())
            .commit()
    }
}