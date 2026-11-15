package com.example.tetris3d.pico.data.repository
import com.example.tetris3d.pico.bridge.GameStateBridge
interface GameRepository { fun snapshot():GameStateBridge.Snapshot?; fun play(); fun pause(); fun resume(); fun bci(enabled:Boolean,focus:Float,mood:Float) }
class NativeGameRepository:GameRepository {
 override fun snapshot()=GameStateBridge.current()
 override fun play()=GameStateBridge.play()
 override fun pause()=GameStateBridge.pause()
 override fun resume()=GameStateBridge.resume()
 override fun bci(enabled:Boolean,focus:Float,mood:Float)=GameStateBridge.setBci(enabled,focus,mood)
}
