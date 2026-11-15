package com.example.tetris3d.pico.platform;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.pico.spatial.ui.platform.stub.SpatialLaunchActivity;
import com.unity3d.player.*;

/** Spatial owns the Activity content; Compose embeds only Unity's game view. */
public class LaunchActivity extends SpatialLaunchActivity implements IUnityPlayerLifecycleEvents, IUnityPlayerSupport {
    private UnityPlayerForActivityOrService player;
    public FrameLayout gameView() {
        if (player == null) {
            player = new UnityPlayerForActivityOrService(this, this);
            player.setRunWithoutFocus(true);
            player.onStart(); player.onResume(); player.windowFocusChanged(true);
        }
        return player.getFrameLayout();
    }
    @Override public UnityPlayer getUnityPlayerConnection() { return player; }
    @Override protected void onStart() { super.onStart(); if (player != null) player.onStart(); }
    @Override protected void onResume() { super.onResume(); if (player != null) player.onResume(); }
    @Override protected void onPause() { if (player != null) player.onPause(); super.onPause(); }
    @Override protected void onStop() { if (player != null) player.onStop(); super.onStop(); }
    @Override public void onWindowFocusChanged(boolean focused) { super.onWindowFocusChanged(focused); if(player != null) player.windowFocusChanged(focused); }
    @Override public void onConfigurationChanged(Configuration config) { super.onConfigurationChanged(config); if(player != null) player.configurationChanged(config); }
    @Override protected void onDestroy() { if(player != null) { player.destroy(); player=null; } super.onDestroy(); }
    @Override public void onUnityPlayerUnloaded() { finish(); }
    @Override public void onUnityPlayerQuitted() { }
}
