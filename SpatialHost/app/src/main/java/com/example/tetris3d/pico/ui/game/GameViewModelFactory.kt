package com.example.tetris3d.pico.ui.game
import com.example.tetris3d.pico.data.repository.*
import com.example.tetris3d.pico.domain.usecase.*
object GameViewModelFactory { val shared by lazy { GameViewModel(GameUseCase(NativeGameRepository()),AccountUseCase(UnconfiguredAccountRepository())) } }
