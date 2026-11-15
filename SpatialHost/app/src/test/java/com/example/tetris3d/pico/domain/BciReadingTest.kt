package com.example.tetris3d.pico.domain
import com.example.tetris3d.pico.domain.model.*
import org.junit.Assert.*
import org.junit.Test
class BciReadingTest {
 @Test fun disconnectedNeverControlsGame() { assertFalse(BciReading().usable(0)) }
 @Test fun staleAndFutureSamplesAreRejected() {
  val r=BciReading(SignalSource.Device,.5f,.5f,1000)
  assertTrue(r.usable(4000)); assertFalse(r.usable(4001)); assertFalse(r.usable(999))
 }
 @Test fun nonFiniteAndOutOfRangeAreRejected() {
  assertFalse(BciReading(SignalSource.Demo,Float.NaN,.5f,1000).usable(1000))
  assertFalse(BciReading(SignalSource.Device,1.1f,.5f,1000).usable(1000))
 }
}
