package com.example.recyclerpersonajes_dialogs_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity3 : AppCompatActivity() {

    lateinit var binding:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}