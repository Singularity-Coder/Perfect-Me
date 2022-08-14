package com.singularitycoder.perfectme.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.singularitycoder.perfectme.helpers.BottomSheetMenu
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    val menuLiveData = MutableLiveData<BottomSheetMenu>()
    val durationLiveData = MutableLiveData<String>()
}