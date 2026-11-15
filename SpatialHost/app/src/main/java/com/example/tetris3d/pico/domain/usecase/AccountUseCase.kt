package com.example.tetris3d.pico.domain.usecase
import com.example.tetris3d.pico.data.repository.AccountRepository
class AccountUseCase(private val repository:AccountRepository) {
 suspend fun login(email:String,password:String)=repository.login(email,password)
 suspend fun sendCode(email:String)=repository.sendCode(email)
 suspend fun register(email:String,code:String,password:String)=repository.register(email,code,password)
}
