package com.singularitycoder.perfectme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singularitycoder.perfectme.databinding.ListItemRoutineStepBinding

class RoutineStepsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var routineStepsList = emptyList<RoutineStep>()
    private var itemClickListener: (routineStep: RoutineStep) -> Unit = {}
    private var stepsClickListener: (routineStep: RoutineStep) -> Unit = {}

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

    fun setStepsClickListener(listener: (routineStep: RoutineStep) -> Unit) {
        stepsClickListener = listener
    }

    inner class RoutineStepViewHolder(
        private val itemBinding: ListItemRoutineStepBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun setData(routineStep: RoutineStep) {
            itemBinding.apply {

            }
        }
    }
}
