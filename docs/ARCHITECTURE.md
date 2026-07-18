# 架构说明

## 当前架构

项目当前是单模块 Android Wear OS 应用：

```text
app
├── presentation
│   ├── MainActivity.kt
│   └── theme
├── res
└── test
```

入口为 `MainActivity`，通过 `setContent` 启动 `WearApp`。`WearApp` 提供主题、`AppScaffold`、Navigation 3 back stack 和 Wear OS swipe-dismiss 场景策略。

## 当前 UI 导航

```text
MenuScreen / GreetingScreen
        │
        └── EdgeButton
                │
                ▼
ListNavScreen / ListScreen
                │
                └── settings icon
                        │
                        ▼
                    SampleDialog
```

导航 key 为 `MenuScreen` 和 `ListNavScreen`，均实现 `AppKey`，并使用 Kotlin Serialization 注解。

## 当前职责

- `MainActivity`：Android Activity 入口和 Compose 内容承载。
- `WearApp`：主题、导航 back stack 和页面注册。
- `GreetingScreen` / `Greeting`：模板欢迎页面。
- `ListScreen`：模板列表、卡片、按钮和 Dialog 状态。
- `SampleDialog`：示例确认/取消 Dialog。
- `presentation.theme`：颜色、排版、主题和卡片默认样式。
- `test/presentation`：Robolectric 下的参数化 Wear OS 截图测试。

## 尚未存在的层

当前不存在以下已实现层：

- ViewModel
- Repository
- Domain/use case
- Network/API client
- Database/local persistence
- Shared mobile companion module

## 最终目标架构

PinWear 的产品定义确认采用 MVVM，目标数据流为：

```text
Presentation
      ↓
ViewModel
      ↓
Repository
      ↓
Retrofit / OkHttp
      ↓
Pinterest Official API
```

约束：

- Screen 不得直接访问 API。
- ViewModel 负责 UI 状态、事件和生命周期协调。
- Repository 负责 Pinterest 数据访问抽象。
- Retrofit/OkHttp 只通过 Repository 被使用。
- Pinterest 官方 API 是唯一远程数据来源。
- 不开发手机端应用。

具体模块拆分、OAuth 组件边界和模型包结构在 v0.2 OAuth 阶段设计前为【待确认】。

## 架构约束

- 保持 Wear OS Compose Material 3 技术路线。
- UI 组件不应承担数据访问和业务决策。
- 页面状态复杂到需要跨重组、配置变化或异步协调时，再引入 ViewModel。
- 新模块或跨模块架构变更必须先完成方案确认。
- 不引入 Pinterest 私有 API、Reverse Engineering 或抓包逻辑。
