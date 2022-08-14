package com.singularitycoder.perfectme.helpers

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singularitycoder.perfectme.databinding.FragmentAddPeriodBottomSheetBinding
import com.singularitycoder.perfectme.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPeriodBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AddPeriodBottomSheetFragment()
    }

    private lateinit var binding: FragmentAddPeriodBottomSheetBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddPeriodBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransparentBackground()
        binding.setupUI()
        binding.setupUserActionListeners()
    }

    private fun FragmentAddPeriodBottomSheetBinding.setupUI() {
        etHours.requestFocus()
        etHours.showKeyboard()
    }

    private fun FragmentAddPeriodBottomSheetBinding.setupUserActionListeners() {
        // TODO set IME options next for every text view and finally button
        // TODO disable space or enter as first char
        // TODO replace text fields with spinning selector
        etHours.doAfterTextChanged { it: Editable? ->
            if (it?.length == 2) etMinutes.requestFocus()
        }
        etMinutes.doAfterTextChanged { it: Editable? ->
            if (it?.length == 2) etSeconds.requestFocus()
        }
        etSeconds.doAfterTextChanged { it: Editable? ->
            if (it?.length == 2) btnDone.requestFocus()
        }
        btnDone.setOnClickListener {
            if (
                etHours.text.isNullOrBlank() &&
                etMinutes.text.isNullOrBlank() &&
                etSeconds.text.isNullOrBlank()
            ) return@setOnClickListener

            var finalDuration = ""
            if (etHours.text.isNullOrBlank().not()) {
                finalDuration += "${etHours.text} hrs"
            }
            if (etMinutes.text.isNullOrBlank().not()) {
                if (finalDuration.isNotBlank()) finalDuration += ", "
                finalDuration += "${etMinutes.text} min"
            }
            if (etSeconds.text.isNullOrBlank().not()) {
                if (finalDuration.isNotBlank()) finalDuration += ", "
                finalDuration += "${etSeconds.text} sec"
            }

            sharedViewModel.durationLiveData.value = finalDuration
            dismiss()
        }
    }
}