package com.example.tetris3d.pico.data.repository
interface AccountRepository {
 val configured:Boolean
 suspend fun login(email:String,password:String):String
 suspend fun sendCode(email:String):String
 suspend fun register(email:String,code:String,password:String):String
}
class UnconfiguredAccountRepository:AccountRepository {
 override val configured=false
 override suspend fun login(email:String,password:String)="账号服务尚未接入，可继续访客游玩。"
 override suspend fun sendCode(email:String)="邮件服务尚未接入，未发送验证码。"
 override suspend fun register(email:String,code:String,password:String)="账号服务尚未接入，未创建账号。"
}
