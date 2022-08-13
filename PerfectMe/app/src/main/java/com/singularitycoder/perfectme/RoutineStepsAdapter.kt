package com.singularitycoder.perfectme

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singularitycoder.perfectme.databinding.ListItemRoutineStepBinding

class RoutineStepsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var routineStepsList = emptyList<RoutineStep>()
    private var itemClickListener: (routineStep: RoutineStep) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ListItemRoutineStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineStepViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RoutineStepViewHolder).setData(routineStepsList[position])
    }

    override fun getItemCount(): Int = routineStepsList.size

    override fun getItemViewType(position: Int): Int = position

    fun setItemClickListener(listener: (routineStep: RoutineStep) -> Unit) {
        itemClickListener = listener
    }

    inner class RoutineStepViewHolder(
        private val itemBinding: ListItemRoutineStepBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(routineStep: RoutineStep) {
            itemBinding.apply {
                tvStepName.text = routineStep.stepName
                tvStepDuration.text = routineStep.stepDuration
                tvStepNumber.text = routineStep.stepNumber.toString()
            }
        }
    }
}
