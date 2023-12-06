package edu.jc.csu411.project1


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ColorStateViewModel : ViewModel() {
    private val _redIntensity = MutableStateFlow(0.0f)
    val redIntensity: StateFlow<Float> = _redIntensity

    private val _greenIntensity = MutableStateFlow(0.0f)
    val greenIntensity: StateFlow<Float> = _greenIntensity

    private val _blueIntensity = MutableStateFlow(0.0f)
    val blueIntensity: StateFlow<Float> = _blueIntensity

    fun setRedIntensity(value: Float) {
        _redIntensity.value = value
    }

    fun setGreenIntensity(value: Float) {
        _greenIntensity.value = value
    }

    fun setBlueIntensity(value: Float) {
        _blueIntensity.value = value
    }
}
