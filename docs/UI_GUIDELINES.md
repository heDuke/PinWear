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

## 产品设计方向

PinWear 遵循 Material Design 3 Expressive，并针对 Pinterest 内容浏览场景采用：

- Round First：圆形屏幕优先。
- OLED 优先：优先深色背景和高对比内容。
- 图片优先：Pin 图片是主要视觉内容。
- 低信息密度：每个屏幕只展示当前任务所需的信息。
- 大触控区域：适配手表触控和误触场景。
- 动画遵循 Material Motion。
- 优先官方 Wear OS Design 和 Material Design 3 Expressive 指导。

MVP 页面方向：OAuth 登录、Boards、Board Pins、Pin 图片、Pin 详情和打开 Pinterest 官方页面。

## Wear OS 适配原则

- 优先使用 Wear OS 专用 Material 3 组件。
- 使用 `TransformingLazyColumn` 处理长列表及边缘变换。
- 保持圆形屏幕和非圆形屏幕上的内容可读性。
- 通过内容 padding、边缘按钮和系统滑动返回适配手表交互。
- 验证不同屏幕尺寸和字体缩放，不依赖单一设备截图。
- 重要动作提供清晰的语义、反馈和可访问描述。
- 图片浏览必须优先考虑加载状态、占位、失败状态和圆形裁切。

## 颜色与排版

- 优先使用 `MaterialTheme.colorScheme`，不在组件中硬编码主题色。
- 所有用户可见文案使用 Android string resource。
- 保持前景色与背景色的可读性。
- 主题、颜色和排版变更必须同时检查深色/动态颜色场景。
- 当前产品品牌色、正式 Typography、图片裁切策略和加载占位样式为【待确认】。

## 交互规范

- 按钮、卡片和图标按钮必须有明确的点击语义。
- 图标按钮必须提供 content description；装饰性图标除外。
- Dialog 必须提供明确的确认、取消和关闭语义。
- 不使用空点击回调作为最终业务实现。
- MVP 不提供点赞、评论、消息、上传或修改 Pinterest 内容的交互。
- 加载、空数据、错误和成功状态的 UI 规范为【待确认】。

## 截图与视觉回归

- 现有截图测试覆盖多个 Wear OS 尺寸和字体缩放组合。
- 视觉变更必须说明影响范围。
- 只有确认 UI 变化是预期结果后，才更新截图基线。
