package com.example.adaptivenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.adaptivenotes.presentation.AdaptiveNotesApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Вираховуємо breakpoint екрана
            val windowSize = calculateWindowSizeClass(this)
            // Запускаємо наш адаптивний додаток
            AdaptiveNotesApp(widthSizeClass = windowSize.widthSizeClass)
        }
    }
}