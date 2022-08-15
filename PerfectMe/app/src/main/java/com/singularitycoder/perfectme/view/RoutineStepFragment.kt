package com.singularitycoder.perfectme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.singularitycoder.perfectme.R

class RoutineStepFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = RoutineStepFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_routine_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onStepComplete()
    }

    private fun onStepComplete() {
        // TODO update routine's last attempt here. On any step complete
    }
}