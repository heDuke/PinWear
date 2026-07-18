# 测试计划

## 当前测试基础

当前测试位于 `app/src/test`，使用：

- JUnit 4
- Robolectric
- Compose UI Test
- Roborazzi 截图测试
- 参数化 `WearDevice` 设备矩阵

现有测试：

- `GreetingScreenTest`：欢迎页面截图。
- `ListScreenTest`：列表初始和滚动后截图。
- `SampleDialogTest`：Dialog 截图。
- `WearScreenshotTest`：统一设备密度、字体缩放、系统 qualifier 和截图逻辑。

## 当前设备矩阵

- Mobvoi TicWatch Pro 5
- Samsung Galaxy Watch 5
- Samsung Galaxy Watch 6 Large
- Google Pixel Watch
- Generic Small Round
- Generic Large Round
- Galaxy Watch 6 small font
- Pixel Watch large font

当前测试基于 Robolectric SDK 33，并对截图设置了不同变化容忍度。

## 验证策略

### UI 视觉验证

- 运行现有 Roborazzi 记录或验证任务。
- 覆盖页面初始状态、滚动状态和 Dialog 状态。
- 对圆形设备、尺寸差异和字体缩放进行检查。

### 交互验证

后续业务开发应补充：

- 首页到列表页的导航。
- 按钮点击行为。
- Dialog 确认、取消和关闭行为。
- 状态变化后的 UI 结果。

### ViewModel/业务测试

如果后续引入 ViewModel 或业务逻辑，应增加不依赖 Android UI 的单元测试，覆盖成功、空数据、失败、取消和重试场景（适用时）。

### 构建验证

- Debug：`./gradlew build lintDebug`
- Release：`./gradlew assembleRelease`
- GitHub Actions 负责执行 CI 构建与 lint。
- tag `v*` 触发 Release APK 构建和上传。

CI 当前在 Gradle 命令中显式关闭 Configuration Cache，以规避现有 Spotless 序列化兼容问题。

## 当前缺口

- 没有业务逻辑测试。
- 没有 ViewModel 测试。
- 没有 API、数据库或同步测试。
- 没有正式设备上的自动化测试矩阵。
- Release 正式签名流程尚未配置。

## 验收要求

业务需求确认后，每项功能至少应明确：

- 正常路径。
- 空数据或首次使用路径。
- 错误与恢复路径。
- Wear OS 不同尺寸和字体缩放表现。
- 自动化测试或明确的人工验证步骤。

