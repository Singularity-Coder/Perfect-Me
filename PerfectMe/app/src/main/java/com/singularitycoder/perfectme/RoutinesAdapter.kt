package com.singularitycoder.perfectme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singularitycoder.perfectme.databinding.ListItemRoutineBinding

class RoutinesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var routinesList = emptyList<Routine>()
    private var itemClickListener: (routine: Routine) -> Unit = {}
    private var stepsClickListener: (routine: Routine) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ListItemRoutineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RoutineViewHolder).setData(routinesList[position])
    }

    override fun getItemCount(): Int = routinesList.size

    override fun getItemViewType(position: Int): Int = position

    fun setItemClickListener(listener: (routine: Routine) -> Unit) {
        itemClickListener = listener
    }

    fun setStepsClickListener(listener: (routine: Routine) -> Unit) {
        stepsClickListener = listener
    }

    inner class RoutineViewHolder(
        private val itemBinding: ListItemRoutineBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun setData(routine: Routine) {
            itemBinding.apply {
                tvRoutineName.text = routine.routineName
                tvRoutineDuration.text = routine.routineDuration
                cardStepsCount.setOnClickListener {
                    stepsClickListener.invoke(routine)
                }
                root.setOnClickListener {
                    itemClickListener.invoke(routine)
                }
            }
        }
    }
}
