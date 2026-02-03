package com.example.viewmodeldemo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class DemoViewModel : ViewModel() {
    var isFahrenheit by mutableStateOf(true)
    var result by mutableStateOf("")
    var inputText by mutableStateOf("") // ← состояние поля ввода

    fun convertTemp() {
        val tempFloat = inputText.toFloatOrNull()
        if (tempFloat == null) {
            result = "Invalid Entry"
            return
        }

        if (isFahrenheit) {
            result = ((tempFloat - 32) * 0.5556).roundToInt().toString()
        } else {
            result = ((tempFloat * 1.8) + 32).roundToInt().toString()
        }
    }

    fun switchChange() {
        isFahrenheit = !isFahrenheit
        result = "" // сброс результата при смене режима
    }
}