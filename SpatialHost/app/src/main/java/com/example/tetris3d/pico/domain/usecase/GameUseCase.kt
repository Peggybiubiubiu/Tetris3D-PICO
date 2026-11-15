package com.example.tetris3d.pico.domain.usecase
import com.example.tetris3d.pico.data.repository.GameRepository
class GameUseCase(private val repository:GameRepository) {
 fun snapshot()=repository.snapshot()
 fun play()=repository.play()
 fun pause()=repository.pause()
 fun resume()=repository.resume()
 fun bci(enabled:Boolean,focus:Float,mood:Float)=repository.bci(enabled,focus,mood)
}
