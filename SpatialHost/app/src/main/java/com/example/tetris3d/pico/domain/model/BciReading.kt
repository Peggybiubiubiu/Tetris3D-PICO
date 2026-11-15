package com.example.tetris3d.pico.domain.model

enum class SignalSource { Disconnected, Demo, Device }
data class BciReading(val source: SignalSource = SignalSource.Disconnected,
    val focus: Float = 0f, val mood: Float = 1f, val timestampMillis: Long = 0L) {
    fun usable(now: Long) = source != SignalSource.Disconnected &&
        focus.isFinite() && mood.isFinite() && focus in 0f..1f && mood in 0f..1f &&
        now - timestampMillis in 0L..3000L
}
