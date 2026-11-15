package com.example.tetris3d.pico.domain.usecase
object AccountValidation {
 fun email(value:String)=Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$").matches(value.trim())
 fun error(email:String,password:String,code:String,register:Boolean):String? = when {
 !email(email)->"请输入有效邮箱地址"
 password.length !in 8..128 -> "密码需为 8–128 个字符"
 register && !Regex("[0-9]{6}").matches(code) -> "请输入 6 位邮箱验证码"
 else -> null
 }
}
