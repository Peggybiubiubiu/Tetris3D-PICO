package com.example.tetris3d.pico.ui.game
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.example.tetris3d.pico.domain.usecase.*
import com.example.tetris3d.pico.domain.model.*
import com.example.tetris3d.pico.domain.eeg.DemoEeg
import com.example.tetris3d.pico.domain.usecase.AccountValidation
class GameViewModel(private val game:GameUseCase,private val account:AccountUseCase):ViewModel() {
 private val mutable=MutableStateFlow(GameUiState()); val state=mutable.asStateFlow()
 private val demo=DemoEeg()
 init { viewModelScope.launch { while(isActive) {
 val snapshot=game.snapshot(); val old=mutable.value
 if(old.source==SignalSource.Demo && !old.frozen)demo.advance(51,old.focus)
 mutable.value=old.copy(ready=snapshot!=null,score=snapshot?.score?:0,seconds=snapshot?.elapsedSeconds?:0f,level=snapshot?.level?:1,progress=snapshot?.timeProgress?:0f,playing=snapshot?.playing?:false,scene=snapshot?.scene?:"",traces=if(old.source==SignalSource.Demo && !old.frozen)demo.snapshot() else if(old.source==SignalSource.Disconnected)emptyArray() else old.traces)
 if(snapshot!=null)game.bci(old.bci && old.source==SignalSource.Demo,old.focus,old.mood)
 delay(200)
 } } }
 fun onEvent(event:GameEvent) { val s=mutable.value; when(event) {
 GameEvent.Play -> if(s.ready)game.play()
 GameEvent.Pause -> if(s.playing)game.pause() else game.resume()
 GameEvent.ToggleBci -> { mutable.value=s.copy(bci=!s.bci); if(s.bci)game.bci(false,s.focus,s.mood) }
 is GameEvent.Focus->mutable.value=s.copy(focus=event.value.coerceIn(0f,1f))
 is GameEvent.Mood->mutable.value=s.copy(mood=event.value.coerceIn(0f,1f))
 is GameEvent.Source->{mutable.value=s.copy(source=event.value,bci=if(event.value==SignalSource.Disconnected)false else s.bci);if(event.value==SignalSource.Disconnected)game.bci(false,0f,1f)}
 GameEvent.Freeze->mutable.value=s.copy(frozen=!s.frozen)
 GameEvent.SwitchAccount->mutable.value=s.copy(register=!s.register,password="",code="",message="")
 is GameEvent.Email->mutable.value=s.copy(email=event.value.take(254),message="")
 is GameEvent.Password->mutable.value=s.copy(password=event.value.take(128),message="")
 is GameEvent.Code->mutable.value=s.copy(code=event.value.filter{it.isDigit()}.take(6),message="")
 GameEvent.ClearSecrets->mutable.value=s.copy(password="",code="",message="")
 GameEvent.SendCode->viewModelScope.launch { mutable.update { it.copy(message=if(!AccountValidation.email(s.email))"请输入有效邮箱地址" else account.sendCode(s.email.trim())) } }
 GameEvent.Submit->viewModelScope.launch { val error=AccountValidation.error(s.email,s.password,s.code,s.register); val result=error?:if(s.register)account.register(s.email.trim(),s.code,s.password)else account.login(s.email.trim(),s.password);mutable.update{it.copy(message=result,password="")} }
 } }
}
