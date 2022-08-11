package com.singularitycoder.perfectme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.singularitycoder.perfectme.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

// Pressing on steps opens steps bottom sheet
// Pressing anywhere else will directly start the routine

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val routinesAdapter = RoutinesAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.setupUI()
    }

    private fun ActivityMainBinding.setupUI() {
        rvContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = routinesAdapter
        }
    }
}