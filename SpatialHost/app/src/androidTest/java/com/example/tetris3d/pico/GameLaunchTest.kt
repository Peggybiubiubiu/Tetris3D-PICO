package com.example.tetris3d.pico
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tetris3d.pico.bridge.GameStateBridge
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class GameLaunchTest {
 @Test fun launchesAndStartsUnityGame() {
  val instrumentation=InstrumentationRegistry.getInstrumentation()
  val context=instrumentation.targetContext
  val intent=context.packageManager.getLaunchIntentForPackage(context.packageName)!!
  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);context.startActivity(intent)
  val deadline=System.currentTimeMillis()+60000
  while(GameStateBridge.current()==null && System.currentTimeMillis()<deadline)Thread.sleep(500)
  assertNotNull("Unity must publish a live game snapshot",GameStateBridge.current())
  instrumentation.runOnMainSync{GameStateBridge.play()}
  val playingDeadline=System.currentTimeMillis()+20000
  while(GameStateBridge.current()?.scene!="TetrisLevel" && System.currentTimeMillis()<playingDeadline)Thread.sleep(500)
  assertEquals("TetrisLevel",GameStateBridge.current()?.scene)
  assertTrue(GameStateBridge.current()!!.playing)
 }
}
