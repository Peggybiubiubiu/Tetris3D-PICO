# Tetris3D PICO window edition

Based on https://github.com/fdmanana/Tetris3D (Filipe Manana, MIT; original LICENSE retained).

This port preserves the original Unity scenes, 5×5×15 grid, piece logic, scoring and audio. It targets Android ARM64 for a PICO OS application window. It does not implement immersive XR or tracked-controller interactions.

Unity: 6000.6.7f1 with Android Build Support. Application ID: com.example.tetris3d.pico (prototype identity).

Build using Unity batch mode with `-buildTarget Android -executeMethod PicoBuild.Android`. Output: Builds/Tetris3D-PICO.apk.

Use the original menu to start. The bottom toolbar moves pieces, rotates them, drops them, pauses, or returns to the menu. Keyboard controls remain available. PICO CLI installs and launches the APK; device compatibility must be verified on target hardware before release.
