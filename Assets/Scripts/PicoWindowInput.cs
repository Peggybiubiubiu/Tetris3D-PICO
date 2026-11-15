using UnityEngine;
using UnityEngine.SceneManagement;
public class PicoWindowInput : MonoBehaviour {
 static KeyCode pending; static int frame;
 public static bool GetKeyDown(KeyCode key) => Input.GetKeyDown(key) || (key == pending && Time.frameCount == frame);
 [RuntimeInitializeOnLoadMethod] static void Init() { var go=new GameObject("PICO Window Controls"); DontDestroyOnLoad(go); go.AddComponent<PicoWindowInput>(); }
 void Button(string label, KeyCode key) { if(GUILayout.Button(label, GUILayout.Width(110),GUILayout.Height(54))) { pending=key; frame=Time.frameCount+1; } }
 void OnGUI() {
 GUI.matrix=Matrix4x4.Scale(new Vector3(Screen.width/1280f,Screen.height/720f,1));
 if(SceneManager.GetActiveScene().name!="TetrisLevel") return;
 GUILayout.BeginArea(new Rect(20,640,1240,70)); GUILayout.BeginHorizontal("box");
 Button("Left",KeyCode.LeftArrow); Button("Right",KeyCode.RightArrow); Button("Forward",KeyCode.UpArrow); Button("Back",KeyCode.DownArrow);
 Button("Rotate X",KeyCode.X); Button("Rotate Y",KeyCode.Z); Button("DROP",KeyCode.Space); Button("Pause",KeyCode.P);
 if(GUILayout.Button("Menu",GUILayout.Width(110),GUILayout.Height(54))) SceneManager.LoadScene("MainMenu");
 GUILayout.EndHorizontal(); GUILayout.EndArea();
 }
}
