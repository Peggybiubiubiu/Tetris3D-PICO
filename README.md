# Tetris3D PICO / 空间方块

PICO Android Spatial 多窗口演示原型，内嵌 Unity 3D 方块游戏。

## 功能
- 5×5×15 立体棋盘，移动、旋转、下落、暂停与计分。
- 独立游戏、成绩、时间、难度及模拟脑电窗口。
- 专注度滑块演示下落速度调节，情绪参数演示颜色调节。
- 32 通道合成 EEG 可视化；未接入真实脑电硬件，也不推断真实情绪或专注度。
- 邮箱登录/注册为界面及接口预留，未接入邮件或账号后端。

## 工程结构
- `Assets/`, `Packages/`, `ProjectSettings/`: Unity 项目。
- `SpatialHost/`: Kotlin PICO Spatial SDK 宿主与导出的 Unity library。
- `README-PICO.md`: 独立 Android 窗口版本说明。
- `SpatialHost/README.md`: 多窗口版本构建、数据边界及验证记录。

## 构建
Unity 6000.6.7f1 + Android Build Support；Android Spatial 宿主使用 PICO Spatial SDK 6.1.9、JDK 21。请使用纯 ASCII 路径，并按上述两个说明文件构建。修改 Unity 后，须重新导出并更新宿主中的 Unity 数据及原生库。

应用 ID：`com.example.tetris3d.pico`；版本：`1.0`。这是原型身份，正式发布前需验证。

## 验证范围
项目附带记录显示，2026-11-05 在 PICO OS 6.1 模拟器中完成安装和部分运行验证，六项 JVM 单元测试通过。完整真机兼容性、部分窗口交互和生命周期尚未验证；设备测试曾在 Unity Activity 销毁阶段崩溃，不应视为通过。以上为随工程提供的记录，本次仓库整理未重新执行构建或设备测试。

独立 Unity APK 仅为 Android 窗口版，不代表沉浸式 XR 或追踪手柄支持。

## 来源与许可
基于 [Filipe Manana / fdmanana/Tetris3D](https://github.com/fdmanana/Tetris3D) 改编，保留原始 MIT [LICENSE](LICENSE) 与署名。第三方依赖仍适用各自许可。
