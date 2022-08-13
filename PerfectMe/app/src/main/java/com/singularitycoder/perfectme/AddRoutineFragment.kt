package com.singularitycoder.perfectme

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.singularitycoder.perfectme.databinding.FragmentAddRoutineBinding
import java.util.*

class AddRoutineFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AddRoutineFragment()
    }

    private lateinit var binding: FragmentAddRoutineBinding

    private val routineStepsAdapter = RoutineStepsAdapter()
    private val routineStepsList = mutableListOf<RoutineStep>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupUI()
        binding.setupUserActionListeners()
    }

    // https://www.youtube.com/watch?v=H9D_HoOeKWM
    private fun FragmentAddRoutineBinding.setupUI() {
        rvRoutineSteps.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = routineStepsAdapter
        }
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            /* Drag Directions */ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            /* Swipe Directions */0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                // FIXME drag is not smooth and gets attached to its immediate next position
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                val fromPositionItem = routineStepsList[fromPosition].apply { stepNumber = toPosition + 1 }
                routineStepsList[fromPosition] = routineStepsList[toPosition].apply { stepNumber = fromPosition + 1 }
                routineStepsList[toPosition] = fromPositionItem
                routineStepsAdapter.notifyItemMoved(fromPosition, toPosition)
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int,
            ) = Unit
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvRoutineSteps)
    }

    private fun FragmentAddRoutineBinding.setupUserActionListeners() {
        ibAddStep.setOnClickListener {
            routineStepsAdapter.routineStepsList = routineStepsList.apply {
                add(RoutineStep(
                    stepNumber = routineStepsList.size + 1,
                    stepName = binding.etAddRoutineStep.text.toString(),
                    stepDuration = "6 mins, 50 sec"
                ))
            }
            routineStepsAdapter.notifyItemInserted(if (routineStepsList.isEmpty()) 0 else routineStepsList.lastIndex)
            binding.etAddRoutineStep.setText("")
        }
        routineStepsAdapter.setItemClickListener { it: RoutineStep ->
        }
        ibBack.setOnClickListener {
            activity?.onBackPressed()
        }
        btnDone.setOnClickListener {
            activity?.onBackPressed()
        }
        // TODO IME check close keyboard
        // TODO on scroll down close keyboard
    }
}