package com.example.tetris3d.pico.ui.game.components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.pico.spatial.ui.design.*
import com.example.tetris3d.pico.domain.model.*

@Composable fun Panel(title:String,content:@Composable ColumnScope.()->Unit) {
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
 Text(title,style=PicoTheme.typography.titleLarge,color=PicoTheme.colorScheme.labelPrimary)
 content()
 }
}
@Composable fun MetricsPanel(s:GameUiState,send:(GameEvent)->Unit,account:()->Unit,close:()->Unit) {
 Panel("本局进展") {
 Text(s.score.toString(),style=PicoTheme.typography.headlineLarge,color=PicoTheme.colorScheme.labelPrimary)
 Text("成绩 · 第 ${s.level} 关",color=PicoTheme.colorScheme.labelPrimary)
 val sec=s.seconds.toInt(); Text("有效用时  %02d:%02d".format(sec/60,sec%60))
 Text("距下一次加速 ${(40*(1-s.progress)).toInt()} 秒")
 LinearProgressIndicator(progress={s.progress},modifier=Modifier.fillMaxWidth())
 Text(if(s.playing)"游戏中" else if(s.scene=="TetrisLevel")"已暂停 / 本局结束" else "等待开始",color=PicoTheme.colorScheme.labelSecondary)
 Button(onClick={send(GameEvent.Pause)},enabled=s.scene=="TetrisLevel"){Text(if(s.playing)"暂停" else "继续")}
 Button(onClick=account){Text("登录 / 注册")}
 Button(onClick=close){Text("收起成绩看板")}
 }
}
@Composable fun BciPanel(s:GameUiState,send:(GameEvent)->Unit,focus:()->Unit,eeg:()->Unit,off:()->Unit,fold:()->Unit) {
 Panel("情绪与专注") {
 Text(if(s.source==SignalSource.Demo)"演示数据 · 非真实脑电推断" else "未连接 EEG 设备",color=PicoTheme.colorScheme.labelSecondary)
 Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) { Text("脑机控制"); Switch(checked=s.bci,onCheckedChange={send(GameEvent.ToggleBci)}) }
 Text("专注 ${(s.focus*100).toInt()}% · 下落 ${"%.2f".format(if(s.bci)1+s.focus else 1f)} 倍")
 Slider(value=s.focus,onValueChange={send(GameEvent.Focus(it))},enabled=s.source==SignalSource.Demo,modifier=Modifier.fillMaxWidth())
 Text("情绪 ${(s.mood*100).toInt()}% · 越低颜色越淡")
 Slider(value=s.mood,onValueChange={send(GameEvent.Mood(it))},enabled=s.source==SignalSource.Demo,modifier=Modifier.fillMaxWidth())
 Text("值平滑变化；3 秒无新数据恢复普通玩法",color=PicoTheme.colorScheme.labelSecondary)
 Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){Button(onClick=focus){Text("专注进度")};Button(onClick=eeg){Text("32 通道 EEG")}}
 Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){Button(onClick={send(GameEvent.Source(SignalSource.Demo))}){Text("演示数据")};Button(onClick={send(GameEvent.Source(SignalSource.Disconnected))}){Text("断开")}}
 Button(onClick=fold){Text("折叠看板")};Button(onClick=off){Text("关闭脑机 · 普通模式")}
 }
}
@Composable fun FocusPanel(s:GameUiState,close:()->Unit) {
 Panel("专注力进度") {
 Text(if(s.source==SignalSource.Disconnected)"未连接" else "${(s.focus*100).toInt()}%",style=PicoTheme.typography.headlineLarge)
 LinearProgressIndicator(progress={if(s.source==SignalSource.Disconnected)0f else s.focus},modifier=Modifier.fillMaxWidth())
 Text("演示值 · 不用于健康判断",color=PicoTheme.colorScheme.labelSecondary)
 Button(onClick=close){Text("关闭")}
 }
}
@Composable fun AccountPanel(s:GameUiState,send:(GameEvent)->Unit,close:()->Unit) {
 Panel(if(s.register)"邮箱注册" else "邮箱登录") {
 Text("账号服务尚未开放，当前可访客游玩。",color=PicoTheme.colorScheme.labelSecondary)
 TextField(value=s.email,onValueChange={send(GameEvent.Email(it))},placeholder={Text("邮箱地址")},singleLine=true,keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Email),modifier=Modifier.fillMaxWidth())
 if(s.register) {
 TextField(value=s.code,onValueChange={send(GameEvent.Code(it))},placeholder={Text("6 位邮箱验证码")},singleLine=true,keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number),modifier=Modifier.fillMaxWidth())
 Button(onClick={send(GameEvent.SendCode)}){Text("获取验证码")}
 }
 TextField(value=s.password,onValueChange={send(GameEvent.Password(it))},placeholder={Text(if(s.register)"设置密码（8–128 字符）" else "密码")},singleLine=true,visualTransformation=PasswordVisualTransformation(),keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Password),modifier=Modifier.fillMaxWidth())
 if(s.message.isNotEmpty())Text(s.message,color=PicoTheme.colorScheme.labelPrimary)
 Button(onClick={send(GameEvent.Submit)}){Text(if(s.register)"注册" else "登录")}
 Button(onClick={send(GameEvent.SwitchAccount)}){Text(if(s.register)"已有账号，去登录" else "邮箱验证码注册")}
 Button(onClick=close){Text("关闭，继续游玩")}
 }
 DisposableEffect(Unit){onDispose{send(GameEvent.ClearSecrets)}}
}
