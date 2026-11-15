package com.example.tetris3d.pico.ui.game.components
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.pico.spatial.ui.design.*
import com.example.tetris3d.pico.domain.model.*
import com.example.tetris3d.pico.domain.eeg.DemoEeg
@Composable fun EegPanel(s:GameUiState,send:(GameEvent)->Unit,close:()->Unit) {
 Column(Modifier.fillMaxSize().padding(24.dp),verticalArrangement=Arrangement.spacedBy(8.dp)) {
 Text("32 通道 EEG · 合成演示",style=PicoTheme.typography.titleLarge)
 Text("256 Hz · 最近 2 秒 · ±60 μV · 非设备实测",color=PicoTheme.colorScheme.labelSecondary)
 val fg=PicoTheme.colorScheme.labelPrimary; val muted=PicoTheme.colorScheme.labelTertiary
 Row(Modifier.weight(1f).fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(24.dp)) {
 for(group in 0..1) Column(Modifier.weight(1f).fillMaxHeight()) {
 for(row in 0..15) {val ch=group*16+row
 Row(Modifier.weight(1f).fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)) {
 Text(DemoEeg.channels[ch],modifier=Modifier.width(48.dp),style=PicoTheme.typography.bodySmall)
 Canvas(Modifier.weight(1f).fillMaxHeight()) {
 drawLine(muted,Offset(0f,size.height/2),Offset(size.width,size.height/2),.5f)
 val values=s.traces.getOrNull(ch)
 if(values!=null) {val path=Path();values.forEachIndexed{i,v->val x=i.toFloat()/(values.size-1)*size.width;val y=size.height/2-v.coerceIn(-60f,60f)/120f*size.height;if(i==0)path.moveTo(x,y)else path.lineTo(x,y)};drawPath(path,fg,style=androidx.compose.ui.graphics.drawscope.Stroke(1.2f))}
 }
 }
 }
 }
 }
 Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
 Button(onClick={send(GameEvent.Freeze)}){Text(if(s.frozen)"继续波形" else "冻结波形")}
 Button(onClick=close){Text("收起 EEG")}
 if(s.source==SignalSource.Disconnected)Text("未连接，无实时数据")
 }
 }
}
