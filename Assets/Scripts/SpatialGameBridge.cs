using UnityEngine;
using UnityEngine.SceneManagement;
// Native host invokes commands by UnitySendMessage; Unity remains the score authority.
public sealed class SpatialGameBridge : MonoBehaviour {
    float nextPublish;
    bool nativeAvailable=true;
    [RuntimeInitializeOnLoadMethod] static void Install() {
        var go=new GameObject("SpatialGameBridge"); DontDestroyOnLoad(go); go.AddComponent<SpatialGameBridge>();
    }
    public void SetBci(string payload) { BciGameModulation.Set(payload); }
    public void Play(string unused) { SceneManager.LoadScene("TetrisLevel"); }
    public void Pause(string unused) { if(GameManager.gm != null) GameManager.gm.SetExternalPaused(true); }
    public void Resume(string unused) { if(GameManager.gm != null) GameManager.gm.SetExternalPaused(false); }
    void Update() {
        BciGameModulation.Step();
        if(Time.unscaledTime < nextPublish || !nativeAvailable) return;
        nextPublish=Time.unscaledTime+0.2f;
#if UNITY_ANDROID && !UNITY_EDITOR
        try {
            var game=GameManager.gm;
            using(var bridge=new AndroidJavaClass("com.example.tetris3d.pico.bridge.GameStateBridge")) {
                bridge.CallStatic("publish",game == null ? 0 : game.CurrentScore,
                    game == null ? 0f : game.ElapsedSeconds, game == null ? 1 : game.CurrentLevel,
                    game == null ? 0f : game.TimeProgress, game != null && game.IsGameActive(),
                    SceneManager.GetActiveScene().name);
            }
        } catch(AndroidJavaException) { nativeAvailable=false; }
#endif
    }
}
