package com.bacancy.focusoncompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CounterViewModel: ViewModel() {

    var count: Int by mutableIntStateOf(0)
    var numbersList: MutableList<Int> = mutableListOf()

}