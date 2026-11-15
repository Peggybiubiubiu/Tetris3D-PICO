using UnityEngine;
using System.Globalization;

// Values are normalized external inputs; this class does not infer mood from EEG.
public static class BciGameModulation {
    public static bool Enabled { get; private set; }
    public static float Focus { get; private set; }
    public static float Mood { get; private set; } = 1f;
    private static float targetFocus, targetMood = 1f, receivedAt;
    public static void Step() {
        if (Enabled && Time.realtimeSinceStartup - receivedAt > 3f) Enabled = false;
        Focus = Mathf.MoveTowards(Focus, Enabled ? targetFocus : 0f, Time.unscaledDeltaTime * 0.5f);
        Mood = Mathf.MoveTowards(Mood, Enabled ? targetMood : 1f, Time.unscaledDeltaTime * 0.5f);
    }
    public static float SpeedMultiplier => Enabled ? 1f + Focus : 1f;
    public static void Set(string payload) {
        var parts=payload.Split('|');
        if(parts.Length!=3 || !float.TryParse(parts[1],NumberStyles.Float,CultureInfo.InvariantCulture,out var focus)
            || !float.TryParse(parts[2],NumberStyles.Float,CultureInfo.InvariantCulture,out var mood)
            || float.IsNaN(focus) || float.IsInfinity(focus) || float.IsNaN(mood) || float.IsInfinity(mood)) return;
        Enabled=parts[0]=="1"; targetFocus=Mathf.Clamp01(focus); targetMood=Mathf.Clamp01(mood); receivedAt=Time.realtimeSinceStartup;
    }
}

public sealed class BciPieceColour : MonoBehaviour {
    Renderer[] renderers;
    Color[] originals;
    MaterialPropertyBlock block;
    void Start() {
        renderers=GetComponentsInChildren<Renderer>(); originals=new Color[renderers.Length];
        block=new MaterialPropertyBlock();
        for(int i=0;i<renderers.Length;i++) originals[i]=renderers[i].sharedMaterial.color;
    }
    void LateUpdate() {
        for(int i=0;i<renderers.Length;i++) {
            if(!renderers[i]) continue;
            var tint=BciGameModulation.Enabled ? Color.Lerp(originals[i],Color.white,(1f-BciGameModulation.Mood)*0.65f) : originals[i];
            renderers[i].GetPropertyBlock(block); block.SetColor("_Color",tint); block.SetColor("_BaseColor",tint);
            renderers[i].SetPropertyBlock(block);
        }
    }
}
