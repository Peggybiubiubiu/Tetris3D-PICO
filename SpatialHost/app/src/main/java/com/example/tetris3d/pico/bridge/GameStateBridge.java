package com.example.tetris3d.pico.bridge;

import com.unity3d.player.UnityPlayer;

/** Immutable snapshots are safely shared by Unity's render thread and native windows. */
public final class GameStateBridge {
    public static final class Snapshot {
        public final int score, level;
        public final float elapsedSeconds, timeProgress;
        public final boolean playing;
        public final String scene;
        public final long updatedAt;
        Snapshot(int score, float elapsed, int level, float progress, boolean playing, String scene) {
            this.score=score; this.elapsedSeconds=elapsed; this.level=level;
            this.timeProgress=progress; this.playing=playing; this.scene=scene;
            this.updatedAt=android.os.SystemClock.elapsedRealtime();
        }
    }
    private static volatile Snapshot latest;
    public static void publish(int score, float elapsed, int level, float progress, boolean playing, String scene) {
        latest=new Snapshot(score,elapsed,level,progress,playing,scene);
    }
    public static Snapshot current() { return latest; }
    public static void play() { UnityPlayer.UnitySendMessage("SpatialGameBridge","Play",""); }
    public static void pause() { UnityPlayer.UnitySendMessage("SpatialGameBridge","Pause",""); }
    public static void resume() { UnityPlayer.UnitySendMessage("SpatialGameBridge","Resume",""); }
    public static void setBci(boolean enabled,float focus,float mood) {
        UnityPlayer.UnitySendMessage("SpatialGameBridge","SetBci",(enabled?"1":"0")+"|"+Float.toString(focus)+"|"+Float.toString(mood));
    }
    private GameStateBridge() { }
}
