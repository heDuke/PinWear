# 开发计划

## 项目简介

PinWear 是一个基于 Pinterest 官方 API 的 Wear OS 原生客户端，使用 Kotlin 和 Jetpack Compose for Wear OS 构建，遵循 Material Design 3 Expressive。

产品定义以 [PROJECT_CONTEXT.md](PROJECT_CONTEXT.md) 为准，架构标准以 [ARCHITECTURE.md](ARCHITECTURE.md) 为准。

## 项目目标

- 打造一个基于 Pinterest 官方 API 的 Wear OS 客户端。
- 让用户在手表上登录 Pinterest、浏览自己的 Boards 和 Pins。
- 提供 Pin 图片和详情浏览能力。
- 支持打开 Pinterest 官方页面。
- 严格遵守 Pinterest Developer Guidelines。
- 仅使用 Pinterest 官方 API，不使用私有 API、Reverse Engineering、抓包或非官方接口。
- 遵循 Presentation → ViewModel → Repository → Remote Data Source → Pinterest API 的目标架构。
- 优先保证圆形屏幕、OLED、图片浏览、低信息密度和大触控区域体验。

MVP 目标和非目标详见 [PROJECT_CONTEXT.md](PROJECT_CONTEXT.md)。

## 技术栈

当前工程技术栈：

- Kotlin 2.4.10
- Android Gradle Plugin 9.4.0-alpha05
- Gradle Wrapper 9.6.1
- Java/Kotlin JVM 17；Gradle daemon toolchain 配置为 21
- Jetpack Compose 与 Compose Compiler
- Compose for Wear OS Material 3 1.6.2
- Navigation 3 与 Wear OS swipe-dismiss 场景策略
- Robolectric、JUnit、Compose UI Test、Roborazzi
- GitHub Actions：Debug 构建/测试/lint，以及 tag 触发的 Release APK 上传

目标技术栈：

- Retrofit
- OkHttp
- Kotlin Serialization
- Coil 3
- Paging 3
- DataStore
- Kotlin Coroutines
- Navigation 3
- Material Design 3 Expressive

目标技术不代表当前已经添加到工程。除非用户明确要求，不得新增其他第三方库；每项技术应在实际阶段出现明确使用场景后再引入。

## 当前架构

当前仓库仍是单 `app` 模块的 Compose Starter UI，尚未实现 ViewModel、Repository、Remote Data Source 或 Pinterest API 客户端。

## 最终架构

项目采用 MVVM 和单向数据流：

```text
Presentation
      ↓
ViewModel
      ↓
Repository
      ↓
Remote Data Source
      ↓
Pinterest Official API
```

具体目录、职责、数据模型、导航、UI State 和错误处理见 [ARCHITECTURE.md](ARCHITECTURE.md)。

## 目录与模块规划

保持单 Gradle `app` 模块，在源码包内划分：

- `core`：通用错误、网络、认证、图片和 Wear UI 基础能力。
- `data`：DTO、Remote Data Source、映射、Token 存储和 Repository 实现。
- `domain`：领域模型和 Repository 接口。
- `presentation`：Screen、Component、Navigation、UI Model、UI State 和 ViewModel。

不新增手机端应用，不新增独立 Gradle 模块，除非用户后续明确要求并完成架构评审。

## MVP

MVP 用户流程：

```text
OAuth 登录 → Boards → Board Pins → Pin 图片 → Pin 详情 → Pinterest 官方页面
```

MVP 不实现：

- 推荐流和首页推荐
- 社交功能、评论、消息、点赞
- 视频和广告
- AI 推荐
- 上传图片
- 修改 Pinterest 内容

## API 规划

第一版计划使用 Pinterest 官方 API：

- OAuth
- `GET /v5/user_account`
- `GET /v5/boards`
- `GET /v5/boards/{board_id}`
- `GET /v5/boards/{board_id}/pins`
- `GET /v5/pins`
- `GET /v5/pins/{pin_id}`

后续版本再评估：

- Search
- Save Pin

所有 API 的字段、权限、回调、分页、限流和错误契约必须以 Pinterest 官方文档核验结果为准。

## UI 规划

目标页面：

1. Splash：恢复会话并决定 Login/Home。
2. Login：发起 Pinterest OAuth 并展示结果。
3. Home：展示用户信息和 Boards。
4. Board：展示 Board 信息和 Pins。
5. Pin Detail：展示 Pin 图片、详情和官方页面入口。

统一状态：Loading、Success、Empty、Error。页面不得直接访问 API。

## 开发阶段

### v0.1：Bootstrap

- [x] 阅读现有项目、源码、测试和工作流。
- [x] 创建统一 Agent 开发规范。
- [x] 初始化项目文档集。
- [x] 完成产品定义、MVP 边界、API 范围和版本路线。

### Architecture Design：架构设计

- [x] 冻结 Presentation → ViewModel → Repository → Remote Data Source → Pinterest API 数据流。
- [x] 规划单 `app` 模块内的 core/data/domain/presentation 目录。
- [x] 规划 DTO、Domain Model 和 UI Model。
- [x] 规划 Auth/User/Board/Pin Repository。
- [x] 规划 Splash/Login/Home/Board/PinDetail ViewModel。
- [x] 规划导航、统一 UI State 和错误处理。
- [x] 将架构决策写入 `ARCHITECTURE.md` 和 `DECISIONS.md`。

### v0.2：OAuth

- [ ] 核验 Pinterest 官方 OAuth 文档和 Developer Guidelines。
- [ ] 确认 OAuth 应用注册、回调方式和权限范围。
- [ ] 确认 Token 生命周期、刷新策略和安全存储策略。
- [ ] 确认 OAuth 错误映射和登录状态转换。
- [ ] 设计 OAuth DTO、Domain Model、Repository 和 ViewModel 契约。
- [ ] 完成 OAuth 方案评审后，才进入 Kotlin 实现。

### v0.3：Boards

- [ ] 核验 `user_account`、`boards` API 官方契约。
- [ ] 设计 User/Board DTO、Domain Model 和 UI Model。
- [ ] 实现 `UserRepository`、`BoardRepository` 及对应 ViewModel。
- [ ] 实现 Boards 加载、空数据、错误、重试和返回流程。
- [ ] 补充 Wear OS 设备和交互测试。

### v0.4：Pins

- [ ] 核验 Board Pins 和 Pins API 官方契约。
- [ ] 设计 Pin DTO、Domain Model、UI Model 和分页策略。
- [ ] 实现 `PinRepository`、`BoardViewModel` 的 Pins 状态。
- [ ] 实现图片加载、占位、失败和限流状态。
- [ ] 验证低信息密度和圆形屏幕布局。

### v0.5：Pin Detail

- [ ] 核验 Pin Detail API 官方契约。
- [ ] 实现 Pin 图片和详情展示。
- [ ] 支持打开 Pinterest 官方页面。
- [ ] 补充详情页返回、错误、无效数据和外部页面测试。

### v0.6：图片浏览优化

- [ ] 优化图片加载、裁切、占位和失败体验。
- [ ] 验证 OLED 优先主题、Material Motion 和触控区域。
- [ ] 完成不同 Wear OS 设备的视觉回归。

### v1.0：正式发布

- [ ] 完成正式签名和安全 GitHub Secrets 配置。
- [ ] 完成 Pinterest Developer Guidelines 合规检查。
- [ ] 完成版本号、Release、兼容性和发布渠道验证。

## 当前阶段

Architecture Design 阶段已完成，当前可以进入 **v0.2 OAuth 的文档核验和方案设计阶段**。

尚未开始 OAuth Kotlin 实现。OAuth 实现必须等待官方文档、权限、回调和 Token 策略核验完成。

## Todo

- [x] 完成 Bootstrap。
- [x] 完成产品定义。
- [x] 完成目标架构设计。
- [x] 完成目录、数据模型、Repository、ViewModel 和导航规划。
- [x] 完成 UI State 和统一错误处理规划。
- [ ] 核验 Pinterest 官方 OAuth/API 文档和 Developer Guidelines。
- [ ] 确认 OAuth 回调、权限范围和 Token 生命周期。
- [ ] 评审并确认架构设计中的待确认事项。
- [ ] 处理或评估现有 Starter 模板残留。
- [ ] 确认正式签名和发布策略。

## 已完成

- [x] 建立 `AGENTS.md`。
- [x] 建立 `docs/` 文档集。
- [x] 建立 `PROJECT_CONTEXT.md` 产品定义。
- [x] 记录现有单模块 Wear OS Compose 架构。
- [x] 记录目标 MVVM 分层架构。
- [x] 记录目标目录、数据模型、Repository、ViewModel、导航、状态和错误处理。
- [x] 记录现有测试矩阵和 CI/Release 流程。

## 下一阶段

下一阶段是 **v0.2 OAuth 文档核验与方案设计**，不是立即开始业务代码开发。

进入 OAuth 实现前必须完成：

- Pinterest 官方 OAuth 文档核验。
- Pinterest Developer Guidelines 合规核验。
- OAuth 回调和权限确认。
- Token 安全存储和生命周期确认。
- OAuth API 契约和测试方案确认。

## 风险

- 当前代码仍是 Starter UI，目标架构尚未落地。
- Pinterest OAuth 权限、回调、Token 策略和 Wear OS 使用约束尚未核验。
- Pinterest API 字段、分页、限流和错误契约尚未核验。
- `GET /v5/pins` 在 MVP 中的具体使用场景仍为【待确认】。
- 不能离线缓存 Pinterest 数据，网络错误和加载策略需要重点设计。
- 图片浏览对网络、功耗、内存和圆形裁切存在较高要求。
- Release 当前使用 debug signing config，不适合正式分发。
- 当前 Gradle/Android 插件及部分依赖较新，兼容性需要通过 CI 持续验证。

## 待确认事项

- Pinterest OAuth 应用注册、回调方式和权限范围。
- Token 生命周期、刷新机制和 DataStore 安全策略。
- Pinterest 官方 API 当前字段、分页、限流、审核和客户端约束。
- `GET /v5/pins` 的 MVP 使用场景。
- Pin 详情字段优先级和图片 URL 选择策略。
- 打开 Pinterest 官方页面的具体方式和失败处理。
- Loading/Empty/Error 的最终 UI 表现。
- 目标包根和 Starter 包名迁移方案。
- 正式签名、发布渠道和版本号规则。
