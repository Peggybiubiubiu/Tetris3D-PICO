package com.example.tetris3d.pico.ui.game
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pico.spatial.ui.design.*
import com.pico.spatial.ui.foundation.dsl.*
import com.pico.spatial.ui.platform.containers.LocalSpatialNavigator
import com.example.tetris3d.pico.platform.LaunchActivity
import com.example.tetris3d.pico.domain.model.*
import com.example.tetris3d.pico.ui.game.components.*
private const val MAIN="TetrisGame"
fun registerGameWindows(scope:SpatialAppScope)=with(scope) {
 val vm=GameViewModelFactory.shared
 DefaultWindowContainer { PicoTheme {
 val s by vm.state.collectAsState();val nav=LocalSpatialNavigator.current
 val activity=LocalContext.current as LaunchActivity
 var previewStarted by remember { mutableStateOf(false) }
 LaunchedEffect(s.ready) {
  val debuggable=(activity.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE)!=0
  if(s.ready && !previewStarted && debuggable && activity.intent.getBooleanExtra("preview",false)) {
   previewStarted=true
   if(!s.bci)vm.onEvent(GameEvent.ToggleBci)
   nav.openWindowContainer("TetrisFocus","focus")
   nav.openWindowContainer("TetrisEeg","eeg")
   vm.onEvent(GameEvent.Play)
  }
 }
 Column(Modifier.fillMaxSize()) {
 AndroidView(factory={activity.gameView()},modifier=Modifier.weight(1f).fillMaxWidth())
 Row(Modifier.fillMaxWidth().padding(12.dp),horizontalArrangement=Arrangement.spacedBy(12.dp)) {
 Button(onClick={vm.onEvent(GameEvent.Play)},enabled=s.ready){Text(if(s.scene=="TetrisLevel")"重新开始" else "Play · 开始")}
 Button(onClick={nav.openWindowContainer("TetrisMetrics","metrics")}){Text("成绩")}
 Button(onClick={nav.openWindowContainer("TetrisBci","bci")}){Text("情绪 / 专注")}
 Button(onClick={nav.openWindowContainer("TetrisAccount","account")}){Text("登录 / 注册")}
 Text(if(s.bci)"脑机演示模式" else "普通模式")
 }
 }
 LaunchedEffect(Unit) {nav.openWindowContainer("TetrisMetrics","metrics");nav.openWindowContainer("TetrisBci","bci")}
 } }
 WindowContainer("TetrisMetrics",form=Form.Planar,defaultSize=WindowContainerSize(360.dp,480.dp),placement={containers.firstOrNull{it.id==MAIN}?.let{Placement.right(it,120.dp)}?:Placement.none()}) {PicoTheme{
 val s by vm.state.collectAsState();val nav=LocalSpatialNavigator.current
 MetricsPanel(s,vm::onEvent,{nav.openWindowContainer("TetrisAccount","account")},{nav.closeWindowContainer("TetrisMetrics","metrics")})
 }}
 WindowContainer("TetrisBci",form=Form.Planar,defaultSize=WindowContainerSize(400.dp,640.dp),placement={containers.firstOrNull{it.id==MAIN}?.let{Placement.left(it,120.dp)}?:Placement.none()}) {PicoTheme{
 val s by vm.state.collectAsState();val nav=LocalSpatialNavigator.current
 fun fold(){if(s.bci)vm.onEvent(GameEvent.ToggleBci);nav.closeWindowContainer("TetrisFocus","focus");nav.closeWindowContainer("TetrisEeg","eeg");nav.closeWindowContainer("TetrisBci","bci")}
 BciPanel(s,vm::onEvent,{nav.openWindowContainer("TetrisFocus","focus")},{nav.openWindowContainer("TetrisEeg","eeg")},{fold()},{fold()})
 }}
 WindowContainer("TetrisFocus",form=Form.Planar,defaultSize=WindowContainerSize(360.dp,300.dp),placement={containers.firstOrNull{it.id=="TetrisBci"}?.let{Placement.top(it,120.dp)}?:Placement.none()}) {PicoTheme{
 val s by vm.state.collectAsState();val nav=LocalSpatialNavigator.current;FocusPanel(s){nav.closeWindowContainer("TetrisFocus","focus")}
 }}
 WindowContainer("TetrisEeg",form=Form.Planar,defaultSize=WindowContainerSize(960.dp,640.dp),placement={containers.firstOrNull{it.id==MAIN}?.let{Placement.bottom(it,120.dp)}?:Placement.none()}) {PicoTheme{
 val s by vm.state.collectAsState();val nav=LocalSpatialNavigator.current;EegPanel(s,vm::onEvent){nav.closeWindowContainer("TetrisEeg","eeg")}
 }}
 WindowContainer("TetrisAccount",form=Form.Planar,defaultSize=WindowContainerSize(480.dp,640.dp),placement={containers.firstOrNull{it.id=="TetrisMetrics"}?.let{Placement.right(it,120.dp)}?:containers.firstOrNull{it.id==MAIN}?.let{Placement.top(it,120.dp)}?:Placement.none()}) {PicoTheme{
 val s by vm.state.collectAsState();val nav=LocalSpatialNavigator.current;AccountPanel(s,vm::onEvent){nav.closeWindowContainer("TetrisAccount","account")}
 }}
}
