package com.example.tetris3d.pico.platform

import android.app.Application
import com.pico.spatial.ui.foundation.dsl.launch
import com.example.tetris3d.pico.mainApp

class SpatialApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        launch(::mainApp)
    }
}
