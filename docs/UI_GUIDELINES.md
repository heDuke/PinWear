# UI 与 Material Design 规范

## 当前基础

项目使用 Compose for Wear OS Material 3，当前主题通过 `dynamicColorScheme` 优先使用动态颜色，否则回退到项目定义的 `wearColorScheme`。

当前使用的 Wear OS 组件包括：

- `AppScaffold`
- `ScreenScaffold`
- `TransformingLazyColumn`
- `ListHeader`
- `TitleCard`
- `Button`
- `ButtonGroup`
- `EdgeButton`
- `FilledIconButton`
- `AlertDialog`

## Wear OS 适配原则

- 优先使用 Wear OS 专用 Material 3 组件。
- 使用 `TransformingLazyColumn` 处理长列表及边缘变换。
- 保持圆形屏幕和非圆形屏幕上的内容可读性。
- 通过内容 padding、边缘按钮和系统滑动返回适配手表交互。
- 验证不同屏幕尺寸和字体缩放，不依赖单一设备截图。
- 重要动作提供清晰的语义、反馈和可访问描述。

## 颜色与排版

- 优先使用 `MaterialTheme.colorScheme`，不在组件中硬编码主题色。
- 所有用户可见文案使用 Android string resource。
- 保持前景色与背景色的可读性。
- 主题、颜色和排版变更必须同时检查深色/动态颜色场景。
- 当前产品品牌色和正式 Typography 规范为【待确认】。

## 交互规范

- 按钮、卡片和图标按钮必须有明确的点击语义。
- 图标按钮必须提供 content description；装饰性图标除外。
- Dialog 必须提供明确的确认、取消和关闭语义。
- 不使用空点击回调作为最终业务实现。
- 加载、空数据、错误和成功状态的 UI 规范为【待确认】。

## 截图与视觉回归

- 现有截图测试覆盖多个 Wear OS 尺寸和字体缩放组合。
- 视觉变更必须说明影响范围。
- 只有确认 UI 变化是预期结果后，才更新截图基线。

