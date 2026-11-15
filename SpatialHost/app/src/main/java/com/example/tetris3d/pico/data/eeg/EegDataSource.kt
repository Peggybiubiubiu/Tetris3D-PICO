package com.example.tetris3d.pico.data.eeg
import com.example.tetris3d.pico.domain.model.BciReading
/** Device adapters supply calibrated microvolt samples and an explicit monotonic timestamp.
 * Focus/mood are optional algorithm outputs, never inferred merely from this waveform contract. */
data class EegFrame(val channelNames:List<String>,val samplesMicrovolts:Array<FloatArray>,val sampleRateHz:Int,val timestampMillis:Long,val estimate:BciReading?=null)
interface EegDataSource {
 val isDemo:Boolean
 suspend fun connect()
 suspend fun nextFrame():EegFrame?
 suspend fun disconnect()
}
