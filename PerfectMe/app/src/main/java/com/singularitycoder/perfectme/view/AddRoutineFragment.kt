package com.singularitycoder.perfectme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.singularitycoder.perfectme.databinding.FragmentAddRoutineBinding
import com.singularitycoder.perfectme.helpers.AddPeriodBottomSheetFragment
import com.singularitycoder.perfectme.helpers.TAG_ADD_PERIOD_MODAL_BOTTOM_SHEET
import com.singularitycoder.perfectme.model.Routine
import com.singularitycoder.perfectme.model.RoutineStep
import com.singularitycoder.perfectme.viewmodel.SharedViewModel

class AddRoutineFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AddRoutineFragment()
    }

    private lateinit var binding: FragmentAddRoutineBinding

    private val routineStepsAdapter = RoutineStepsAdapter()
    private val routineStepsList = mutableListOf<RoutineStep>()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupUI()
        binding.setupUserActionListeners()
        observeForData()
    }

    private fun observeForData() {
        sharedViewModel.durationLiveData.observe(viewLifecycleOwner) { duration: String? ->
            routineStepsAdapter.routineStepsList = routineStepsList.apply {
                add(RoutineStep(
                    stepNumber = routineStepsList.size + 1,
                    stepName = binding.etAddRoutineStep.text.toString(),
                    stepDuration = duration ?: ""
                ))
            }
            routineStepsAdapter.notifyItemInserted(if (routineStepsList.isEmpty()) 0 else routineStepsList.lastIndex)
            binding.etAddRoutineStep.setText("")
        }
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
            if (etAddRoutineStep.text.isNullOrBlank()) return@setOnClickListener
            AddPeriodBottomSheetFragment.newInstance().show(requireActivity().supportFragmentManager, TAG_ADD_PERIOD_MODAL_BOTTOM_SHEET)
        }
        routineStepsAdapter.setItemClickListener { it: RoutineStep ->
        }
        ibBack.setOnClickListener {
            activity?.onBackPressed()
        }
        btnDone.setOnClickListener {
            (activity as? MainActivity)?.addRoutine(
                Routine(
                    routineName = etRoutineName.editText?.text.toString(),
                    routineDuration = "",
                    stepsCount = routineStepsList.size
                )
            )
            activity?.onBackPressed()
        }
        // TODO IME check close keyboard
        // TODO on scroll down close keyboard
    }
}