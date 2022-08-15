package com.singularitycoder.perfectme.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.singularitycoder.perfectme.databinding.ActivityMainBinding
import com.singularitycoder.perfectme.helpers.*
import com.singularitycoder.perfectme.model.Routine
import dagger.hilt.android.AndroidEntryPoint

// Pressing on steps opens steps bottom sheet
// Pressing anywhere else will directly start the routine
// 10 sec countdown before starting any step. Play sound on start
// Bla Bla Bla
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val routinesAdapter = RoutinesAdapter()

    private lateinit var binding: ActivityMainBinding

    private val routineList = mutableListOf<Routine>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.setupUI()
        binding.setupUserActionListeners()
    }

    private fun ActivityMainBinding.setupUI() {
        rvContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = routinesAdapter
        }
    }

    private fun ActivityMainBinding.setupUserActionListeners() {
        routinesAdapter.setStepsClickListener { it: Routine ->
            val routineStepsOptionsList = listOf(
                BottomSheetMenu(1, "Update skill", android.R.drawable.ic_delete),
                BottomSheetMenu(2, "Delete skill", android.R.drawable.ic_delete),
            )
            MenuBottomSheetFragment.newInstance(routineStepsOptionsList).show(supportFragmentManager, TAG_MENU_MODAL_BOTTOM_SHEET)
        }
        fabAddRoutine.setOnClickListener {
            showScreen(AddRoutineFragment.newInstance(), TAG_ADD_ROUTINE_FRAGMENT)
        }
    }

    fun addRoutine(routine: Routine) {
        routinesAdapter.routineList = routineList.apply { add(routine) }
        routinesAdapter.notifyItemInserted(routineList.size)
    }
}