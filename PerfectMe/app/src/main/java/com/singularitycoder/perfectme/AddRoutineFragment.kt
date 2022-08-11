package com.singularitycoder.perfectme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.singularitycoder.perfectme.databinding.FragmentAddRoutineBinding

class AddRoutineFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AddRoutineFragment()
    }

    private lateinit var binding: FragmentAddRoutineBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }
}