# Tetris3D PICO
Unity Android window port of fdmanana/Tetris3D, MIT license retained. SpatialHost contains the Kotlin PICO Spatial SDK host with independent native windows and embeds the Unity game view. The standalone Unity APK remains a window-only build.

Keep original game logic and scenes. PicoWindowInput provides touch toolbar input without removing keyboard support. Assets/AppIcon.png is the user-supplied app icon. Assets/Editor/PicoBuild.cs configures ARM64 IL2CPP and packages both scenes.

Use Unity 6000.6.7f1 Android Build Support. Android build tools require an ASCII-only project path: sync a build copy into /tmp/Tetris3D-PICO-build, excluding Temp and Logs, then run Unity batch mode with -buildTarget Android -executeMethod PicoBuild.Android. Return the APK and logs to this project. Do not claim immersive PICO compatibility based on this window build.

Use pico-cli for device installation, launch, logs and screenshots. Target emulator-5554 only after checking it is the intended connected PICO emulator. Preserve LICENSE and upstream attribution.
