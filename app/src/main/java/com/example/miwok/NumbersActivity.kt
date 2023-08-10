package com.example.miwok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
private const val TAG = "NumbersActivity"
class NumbersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: called")
        supportFragmentManager.beginTransaction()
            .replace(R.id.category_container,NumbersFragment())
            .commit()
    }
}

