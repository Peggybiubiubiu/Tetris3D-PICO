package com.example.tetris3d.pico.domain.model
 data class GameUiState(val ready:Boolean=false,val score:Int=0,val seconds:Float=0f,val level:Int=1,val progress:Float=0f,val playing:Boolean=false,val scene:String="",val bci:Boolean=false,val source:SignalSource=SignalSource.Demo,val focus:Float=.5f,val mood:Float=.8f,val frozen:Boolean=false,val traces:Array<FloatArray> = emptyArray(),val register:Boolean=false,val email:String="",val password:String="",val code:String="",val message:String="")
 sealed interface GameEvent {
 data object Play:GameEvent; data object Pause:GameEvent; data object ToggleBci:GameEvent
 data class Focus(val value:Float):GameEvent; data class Mood(val value:Float):GameEvent
 data class Source(val value:SignalSource):GameEvent; data object Freeze:GameEvent
 data object SwitchAccount:GameEvent; data class Email(val value:String):GameEvent
 data class Password(val value:String):GameEvent; data class Code(val value:String):GameEvent
 data object Submit:GameEvent; data object SendCode:GameEvent; data object ClearSecrets:GameEvent
 }
