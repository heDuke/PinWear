# 开发计划

## 项目简介

PinWear 是一个基于 Pinterest 官方 API 的 Wear OS 原生客户端，使用 Kotlin 和 Jetpack Compose for Wear OS 构建，遵循 Material Design 3 Expressive。

产品定义以 [PROJECT_CONTEXT.md](PROJECT_CONTEXT.md) 为准。当前代码仍基于 Wear OS Compose Starter Sample，工程名、namespace、Kotlin 包名和部分用户文案仍保留 Starter 相关内容。

## 项目目标

- 打造一个基于 Pinterest 官方 API 的 Wear OS 客户端。
- 让用户在手表上登录 Pinterest、浏览自己的 Boards 和 Pins。
- 提供 Pin 图片和详情浏览能力。
- 支持打开 Pinterest 官方页面。
- 严格遵守 Pinterest Developer Guidelines。
- 仅使用 Pinterest 官方 API，不使用私有 API、Reverse Engineering、抓包或非官方接口。
- 优先保证圆形屏幕、OLED、图片浏览、低信息密度和大触控区域体验。

MVP 目标和非目标详见 [PROJECT_CONTEXT.md](PROJECT_CONTEXT.md)。

## 技术栈

- Kotlin 2.4.10
- Android Gradle Plugin 9.4.0-alpha05
- Gradle Wrapper 9.6.1
- Java/Kotlin JVM 17；Gradle daemon toolchain 配置为 21
- Jetpack Compose 与 Compose Compiler
- Compose for Wear OS Material 3 1.6.2
- Navigation 3 与 Wear OS swipe-dismiss 场景策略
- 计划中的网络和数据技术：Retrofit、OkHttp、Kotlin Serialization、Coil 3、Paging 3、DataStore、Kotlin Coroutines
- Robolectric、JUnit、Compose UI Test、Roborazzi
- GitHub Actions：Debug 构建/测试/lint，以及 tag 触发的 Release APK 上传

计划技术不代表当前已经添加到工程。除非用户明确要求，不得新增其他第三方库。当前配置版本来自仓库，未进行版本兼容性判断。

## 架构设计

当前为单模块、以 UI 为中心的 Compose 架构；最终目标采用 MVVM：

```text
app
└── MainActivity
    └── WearApp
        ├── GreetingScreen
    └── ListScreen
        └── SampleDialog
```

目标数据流：

```text
Presentation → ViewModel → Repository → Retrofit/OkHttp → Pinterest Official API
```

当前没有已实现的 ViewModel、Repository、数据层、网络层或持久化层。Screen 不得直接访问 API。

## 模块划分

当前模块：

- `app`：唯一 Android 应用模块，包含 Activity、Compose UI、主题、资源和单元/截图测试。

当前没有独立的 domain、data、network、wear companion 或移动端模块。

## MVP

MVP 用户流程：

```text
OAuth 登录 → Boards → Board Pins → Pin 图片 → Pin 详情 → Pinterest 官方页面
```

MVP 不实现推荐流、社交功能、评论、消息、点赞、视频、广告、AI 推荐、上传图片和修改 Pinterest 内容。

## API 规划

第一版计划使用 Pinterest 官方 API：

- OAuth
- `GET /v5/user_account`
- `GET /v5/boards`
- `GET /v5/boards/{board_id}`
- `GET /v5/boards/{board_id}/pins`
- `GET /v5/pins`
- `GET /v5/pins/{pin_id}`

Search 和 Save Pin 留待后续版本评估。具体字段、权限、回调方式、限流和错误契约必须以 Pinterest 官方文档核验结果为准。

## UI 规划

当前 UI 为模板演示界面：欢迎页、列表页、示例卡片、示例按钮、设置图标按钮、点赞图标按钮和示例 Dialog。

MVP 页面规划为：OAuth 登录、Boards、Board Pins、Pin 图片、Pin 详情和打开官方页面。UI 约束见 [UI_GUIDELINES.md](UI_GUIDELINES.md)，具体视觉方案仍有待确认项。

## 开发阶段

### v0.1：Bootstrap

- [x] 阅读现有项目、源码、测试和工作流。
- [x] 创建统一 Agent 开发规范。
- [x] 初始化架构、API、UI、测试、决策和变更日志文档。
- [x] 记录当前项目状态、风险和待确认事项。
- [x] 完成产品定义、MVP 边界、API 范围和版本路线。

### v0.2：OAuth

- [ ] 核验 Pinterest 官方 OAuth 文档和 Developer Guidelines。
- [ ] 确认 OAuth 应用注册、回调方式和权限范围。
- [ ] 设计登录状态、Token 安全存储和退出登录流程。
- [ ] 完成 OAuth 方案评审后，才进入实现。

### v0.3：Boards

- [ ] 设计 `user_account` 和 Boards 数据模型。
- [ ] 建立 Presentation → ViewModel → Repository → Retrofit 的数据流。
- [ ] 实现 Boards 浏览、加载、空数据和错误状态。
- [ ] 补充 Wear OS 设备和交互测试。

### v0.4：Pins

- [ ] 设计 Board Pins 数据模型和分页策略。
- [ ] 实现 Board 详情及 Pins 浏览。
- [ ] 处理图片 URL、加载、失败和限流状态。
- [ ] 验证低信息密度和圆形屏幕布局。

### v0.5：Pin Detail

- [ ] 实现 Pin 图片和详情展示。
- [ ] 支持打开 Pinterest 官方页面。
- [ ] 补充详情页返回、错误和无效数据测试。

### v0.6：图片浏览优化

- [ ] 优化图片加载、裁切、占位和失败体验。
- [ ] 验证 OLED 优先主题、动效和触控区域。
- [ ] 完成不同 Wear OS 设备的视觉回归。

### v1.0：正式发布

- [ ] 完成正式签名和安全 GitHub Secrets 配置。
- [ ] 完成 Pinterest Guidelines 合规检查。
- [ ] 完成版本号、Release、兼容性和发布渠道验证。

## 当前阶段

当前处于 **v0.1：Bootstrap**。产品定义已完成，尚未开始 OAuth 或其他业务功能开发。

## Todo

- [x] 确认 PinWear 产品类型、目标平台、技术路线和产品边界。
- [x] 确认 MVP 用户流程和非目标功能。
- [x] 确认第一版 API 路径范围和后续 API 方向。
- [x] 确认最终目标架构和设计原则。
- [ ] 核验 Pinterest 官方 OAuth/API 文档和 Developer Guidelines。
- [ ] 确认 OAuth 回调、权限范围和 Token 生命周期。
- [ ] 处理或评估现有 Starter 模板残留。
- [ ] 确认正式签名和发布策略。

## 已完成

- [x] 建立 `AGENTS.md`。
- [x] 建立 `docs/` 文档集。
- [x] 记录当前单模块 Wear OS Compose 架构。
- [x] 记录现有测试矩阵和 CI/Release 流程。

## 下一阶段

下一阶段是 **v0.2：OAuth**。开始实现前，必须完成 Pinterest 官方 OAuth 文档和 Developer Guidelines 核验，并确认 OAuth 应用配置、回调方式、权限范围和 Token 安全策略。

## 风险

- 工程仍有大量 Compose Starter 命名和示例文案，可能造成产品识别和维护混淆。
- Release 当前使用 debug signing config，不适合正式分发。
- 应用尚未实现目标中的 ViewModel、Repository、Retrofit、OAuth 或 API 数据流。
- Pinterest OAuth 权限、回调方式、API 字段、限流和 Wear OS 使用约束尚未核验。
- Pinterest Developer Guidelines 合规要求需要在实现前逐项检查。
- 当前 Gradle/Android 插件及部分依赖为较新的版本，兼容性需要通过 CI 持续验证。
- CI 已针对 Spotless 与 Gradle Configuration Cache 的问题关闭 CI 中的 Configuration Cache；该策略尚未在本地环境完整复验。

## 待确认事项

- Pinterest OAuth 应用的注册信息、回调方式和权限范围是什么？
- Pinterest 官方 API 当前允许的字段、限流、审核和客户端约束是什么？
- 打开 Pinterest 官方页面的具体方式和失败处理是什么？
- Pin 详情在手表上的字段优先级是什么？
- 图片加载失败、超时、限流和无网络状态如何呈现？
- 正式发布的签名、渠道和版本号规则是什么？
