using UnityEditor;
using UnityEditor.Build.Reporting;
using UnityEngine;
public static class PicoBuild {
 public static void Android() {
 PlayerSettings.SetIconsForTargetGroup(BuildTargetGroup.Unknown, new[]{AssetDatabase.LoadAssetAtPath<Texture2D>("Assets/AppIcon.png")});
 var icon=AssetDatabase.LoadAssetAtPath<Texture2D>("Assets/AppIcon.png");
 foreach(var kind in PlayerSettings.GetSupportedIconKinds(UnityEditor.Build.NamedBuildTarget.Android)) {
 var slots=PlayerSettings.GetPlatformIcons(BuildTargetGroup.Android,kind);
 foreach(var slot in slots) { var layers=new Texture2D[slot.maxLayerCount]; for(int i=0;i<layers.Length;i++) layers[i]=icon; slot.SetTextures(layers); }
 PlayerSettings.SetPlatformIcons(BuildTargetGroup.Android,kind,slots);
 }
 PlayerSettings.companyName="Tetris3D Community"; PlayerSettings.productName="Tetris3D PICO";
 PlayerSettings.SetApplicationIdentifier(UnityEditor.Build.NamedBuildTarget.Android,"com.example.tetris3d.pico");
 PlayerSettings.SetScriptingBackend(UnityEditor.Build.NamedBuildTarget.Android,ScriptingImplementation.IL2CPP);
 PlayerSettings.Android.targetArchitectures=AndroidArchitecture.ARM64;
 PlayerSettings.Android.minSdkVersion=AndroidSdkVersions.AndroidApiLevel29;
 PlayerSettings.defaultInterfaceOrientation=UIOrientation.LandscapeLeft;
 PlayerSettings.Android.androidIsGame=true;
 var report=BuildPipeline.BuildPlayer(new BuildPlayerOptions {scenes=new[]{"Assets/Scenes/MainMenu.unity","Assets/Scenes/TetrisLevel.unity"},locationPathName="Builds/Tetris3D-PICO.apk",target=BuildTarget.Android,options=BuildOptions.Development});
 if(report.summary.result!=BuildResult.Succeeded) throw new System.Exception("APK build failed: "+report.summary.result);
 }
}
