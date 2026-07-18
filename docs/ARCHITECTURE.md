# 架构设计

## 设计状态

本文档定义 PinWear 的目标架构，作为后续实现的统一架构标准。

当前仓库仍是单 `app` 模块的 Wear OS Compose Starter 样例。以下目录、模型、Repository 和 ViewModel 均为架构设计，不代表已经存在对应代码。

产品范围以 [PROJECT_CONTEXT.md](PROJECT_CONTEXT.md) 为准，开发规范以根目录 [AGENTS.md](../AGENTS.md) 为准。

## 架构总览

PinWear 遵循 MVVM 和单向数据流：

```text
┌──────────────────────────────────────┐
│ Presentation                          │
│ Screen / Component / Navigation      │
└──────────────────┬───────────────────┘
                   │ UI events / UiState
                   ▼
┌──────────────────────────────────────┐
│ ViewModel                             │
│ state, event, lifecycle coordination  │
└──────────────────┬───────────────────┘
                   │ repository calls
                   ▼
┌──────────────────────────────────────┐
│ Repository                            │
│ Auth / User / Board / Pin contracts   │
└──────────────────┬───────────────────┘
                   │ data operations
                   ▼
┌──────────────────────────────────────┐
│ Remote Data Source                    │
│ OAuth client / Pinterest API client   │
└──────────────────┬───────────────────┘
                   │ HTTPS, official API only
                   ▼
┌──────────────────────────────────────┐
│ Pinterest Official API                │
└──────────────────────────────────────┘
```

数据返回方向相反：Pinterest API → Remote Data Source → Repository → ViewModel → Screen。

## 依赖关系与调用方向

### 允许的依赖方向

```text
Presentation → ViewModel → Domain Repository Contract
Data Repository Implementation → Domain Model / Repository Contract
Remote Data Source → Retrofit/OkHttp API Contract
Data Mapper → DTO / Domain Model
```

应用组合根负责把具体 Repository 实现注入 ViewModel。Screen 不得直接依赖 Retrofit、OkHttp、DTO 或 Pinterest API。

### 禁止的依赖方向

- Screen → Retrofit/OkHttp
- Screen → Pinterest API
- ViewModel → Android View 或 Composable 实例
- Domain Model → Retrofit、Android UI 或具体数据源
- DTO → Presentation UI
- Remote Data Source → Screen
- 任何层 → Pinterest 私有 API、抓包结果或 Reverse Engineering 代码

## 分层职责

### Presentation

负责 Wear OS UI 展示、用户输入、导航和可访问性。

- 订阅 ViewModel 暴露的不可变 UI State。
- 将用户操作转换为 UI Event 传给 ViewModel。
- 只使用 UI Model，不处理 DTO 和 HTTP 响应。
- 负责加载、成功、空数据和错误状态的呈现。
- 负责圆形屏幕、OLED、图片优先和大触控区域适配。

### ViewModel

负责页面状态、事件协调和生命周期感知的异步工作。

- 接收 UI Event。
- 调用一个或多个 Repository。
- 将 Domain Model 转换为 UI Model，或委托给 Presentation Mapper。
- 暴露不可变的 `UiState` 和一次性导航/外部打开事件。
- 管理取消、重试和页面重新进入行为。
- 不持有 Activity、Composable、View 或 Context 引用。

### Repository

负责定义业务可用的数据访问接口，并隐藏远程数据源细节。

- 向 ViewModel 提供 Domain Model 或领域错误。
- 协调 Remote Data Source 和认证会话。
- 处理数据源选择、分页参数和统一错误映射。
- 不向上层暴露 DTO、Retrofit Response 或 HTTP 实现细节。
- 不缓存 Pinterest 内容；仅允许为 OAuth 会话安全存储必要的 Token 信息。

Repository 接口位于 Domain 层，具体实现位于 Data 层。

### Remote Data Source

负责与 Pinterest 官方 API 通信。

- 使用 Retrofit/OkHttp 发起官方 API 请求。
- 负责请求参数、认证 header、响应解析和底层网络错误。
- 提供 OAuth 相关远程操作和 Pinterest API 操作。
- 将 HTTP/API 原始错误交给 Data 层统一映射。
- 不包含 UI 状态和页面导航逻辑。

### Pinterest API

唯一允许的远程 Pinterest 数据来源。所有能力必须经过官方文档和 Pinterest Developer Guidelines 核验。

## 目标目录结构

项目保持单 Gradle `app` 模块，采用包级分层，不新增 `core`、`data`、`domain` 等 Gradle 子模块。以下是目标物理目录；`<base-package>` 表示最终 Kotlin 包根，具体包名迁移需单独确认：

```text
app/
├── src/
│   ├── main/
│   │   ├── java/<base-package>/
│   │   │   ├── App.kt                         # 应用组合根（规划）
│   │   │   ├── core/
│   │   │   │   ├── common/                    # Result、错误、通用扩展
│   │   │   │   ├── network/                   # HTTP/Retrofit 通用配置
│   │   │   │   ├── auth/                      # Token 注入和会话基础设施
│   │   │   │   ├── image/                     # 图片加载抽象和通用策略
│   │   │   │   └── ui/                        # 通用 Wear UI 基础组件
│   │   │   ├── data/
│   │   │   │   ├── local/auth/                # OAuth Token/会话安全存储
│   │   │   │   ├── remote/pinterest/
│   │   │   │   │   ├── api/                   # Retrofit API 定义
│   │   │   │   │   ├── datasource/            # Pinterest Remote Data Source
│   │   │   │   │   ├── dto/                   # API 请求/响应 DTO
│   │   │   │   │   └── mapper/                # DTO → Domain Model
│   │   │   │   └── repository/                # Repository 实现
│   │   │   ├── domain/
│   │   │   │   ├── model/                     # User、Board、Pin 等领域模型
│   │   │   │   └── repository/                # Repository 接口
│   │   │   └── presentation/
│   │   │       ├── navigation/               # 路由、导航图和外部页面事件
│   │   │       ├── ui/                       # 通用 Screen/Component/UiModel
│   │   │       ├── theme/                    # Wear Material 3 Expressive 主题
│   │   │       ├── splash/                   # Splash Screen + ViewModel
│   │   │       ├── login/                    # Login Screen + ViewModel
│   │   │       ├── home/                     # Home/Boards Screen + ViewModel
│   │   │       ├── board/                    # Board Screen + ViewModel
│   │   │       └── pindetail/                # Pin Detail Screen + ViewModel
│   │   └── res/
│   └── test/
│       ├── java/<base-package>/              # 单元、Repository、ViewModel 测试
│       └── screenshots/                      # Roborazzi 视觉基线
└── ...
```

目录说明：

- `core`：跨业务复用的基础能力，不包含 Pinterest 业务规则。
- `data`：远程/本地数据访问、DTO、映射和 Repository 实现。
- `domain`：与数据来源无关的领域模型和 Repository 契约。
- `presentation`：页面、组件、导航、UI Model、UI State 和 ViewModel。
- `data/local/auth`：Android Keystore 负责管理加密密钥。Access Token 和 Refresh Token 使用该密钥进行加密后，保存到本地持久化存储。不缓存 Pinterest Boards、Pins 或图片数据。

## 数据模型设计

### DTO

DTO 只表达 Pinterest API 契约，不得直接传给 UI：

- `OAuthTokenDto`
- `UserAccountDto`
- `BoardDto`
- `BoardDetailDto`
- `PinDto`
- `PinDetailDto`
- `ImageDto`
- `PagingDto`
- `PinterestErrorDto`

字段、可空性和嵌套结构必须以 Pinterest 官方 API 文档核验结果为准，未核验字段标记为【待确认】。

### Domain Model

Domain Model 表达 PinWear 的业务语义，与 Retrofit 和 JSON 字段无关：

- `UserAccount`
- `Board`
- `BoardPinsPage`
- `Pin`
- `PinDetail`
- `PinImage`
- `PageCursor`

Token 作为认证会话领域数据单独管理，不进入普通 Pin/Board UI 模型。

### UI Model

当 API 字段与页面展示需求不一致时使用 UI Model：

- `BoardUiModel`
- `PinUiModel`
- `PinDetailUiModel`
- `UserUiModel`

UI Model 只包含当前页面所需字段、展示文本、图片展示信息和可访问性描述，不暴露 DTO 或底层错误。

### 数据转换

```text
Pinterest JSON
      ↓ Retrofit/Kotlin Serialization
DTO
      ↓ Data Mapper
Domain Model
      ↓ ViewModel/Presentation Mapper
UI Model
      ↓
Composable Screen
```

转换规则：

- DTO → Domain：处理 API 字段、可空值、分页和数据契约变化。
- Domain → UI：处理展示文案、图片选择、格式化和可访问性信息。
- 映射失败必须进入统一错误处理，不得在 UI 中静默吞掉。

## Repository 设计

Repository 接口位于 `domain/repository`，实现位于 `data/repository`。

### AuthRepository

职责：

- 检查当前 OAuth 会话状态。
- 启动 Pinterest 官方 OAuth 流程。
- 客户端直接调用 `POST /v5/oauth/token` 进行 Token 交换。
- 管理 Token 刷新策略：整个应用任意时刻只能存在一个 Refresh 请求。其它收到 401 的请求等待当前 Refresh 完成。Refresh 成功后统一重放请求。
- 区分 Refresh 失败策略：
  - 网络错误（如 Timeout、DNS、无网络）：不得清除 Token，保留当前登录状态，等待用户重试或下次请求重新刷新。
  - 认证错误（如 invalid_grant、Refresh Token 已失效）：执行 Logout 流程，清除认证信息并返回 Login。
- 处理 Logout 流程：清除所有认证信息，并导航回 Login 页面。
- 提供登录、退出和会话恢复结果。

OAuth 的具体授权 URL、回调方式、权限范围和 Token 生命周期必须先完成官方文档核验。

### UserRepository

职责：

- 获取当前登录用户账号信息。
- 将 `/v5/user_account` 响应转换为 `UserAccount`。
- 暴露用户加载、空结果和错误结果。

### BoardRepository

职责：

- 获取当前用户 Boards。
- 获取单个 Board 详情。
- 处理 Boards 分页。
- 将 Board 相关 DTO 转换为 Domain Model。

对应计划接口：`GET /v5/boards`、`GET /v5/boards/{board_id}`。

### PinRepository

职责：

- 获取 Board 中的 Pins。
- 获取 Pin 列表或指定 Pin 信息。
- 获取单个 Pin 详情。
- 处理 Pin 分页和图片信息。

对应计划接口：`GET /v5/boards/{board_id}/pins`、`GET /v5/pins`、`GET /v5/pins/{pin_id}`。

## ViewModel 设计

ViewModel 位于对应的 `presentation/<feature>` 包，向 Screen 暴露不可变 State 和事件入口。

### SplashViewModel

- 检查 OAuth 会话是否存在且可用。
- 决定进入 Login 或 Home。
- 管理会话恢复中的 Loading 和恢复失败状态。
- 不展示业务数据。

### LoginViewModel

- 管理登录入口和 OAuth 回调结果。
- 触发官方 OAuth 流程。
- 管理登录中、成功、取消和失败状态。
- 登录成功后发出进入 Home 的一次性导航事件。

### HomeViewModel

- 加载当前用户信息和 Boards 概览。
- 管理 Boards 的 Loading、Success、Empty 和 Error。
- 处理刷新、重试和进入 Board 的事件。
- 不直接请求 Retrofit。

### BoardViewModel

- 加载 Board 详情。
- 加载 Board 中的 Pins。
- 管理分页、刷新、图片预览入口和错误恢复。
- 处理进入 Pin Detail 的导航事件。

### PinDetailViewModel

- 加载指定 Pin 详情。
- 提供图片、标题、描述和官方页面入口所需的 UI State。
- 管理加载、错误和重试。
- 发出打开 Pinterest 官方页面的外部事件。
- 不实现点赞、保存、评论或修改 Pin。

## 导航设计

目标导航流程：

```text
Splash
  ├── 未登录/会话失效 → Login
  └── 已登录 → Home

Login
  └── OAuth 成功 → Home

Home
  ├── 选择 Board → Board
  └── Logout → 清除所有认证信息 → Login

Board
  └── 选择 Pin → Pin Detail

Pin Detail
  └── 打开官方页面 → Pinterest Official Page
```

页面职责：

- `Splash`：只负责启动时会话判断和路由决策。
- `Login`：只负责 Pinterest OAuth 登录入口和结果反馈。
- `Home`：展示用户身份和 Boards 入口。
- `Board`：展示 Board 信息和 Pins 列表。
- `Pin Detail`：展示 Pin 图片、详情和官方页面入口。
- `Pinterest Official Page`：外部页面，不属于 PinWear 自己的业务页面。

Navigation 3 的具体 route key、参数序列化方式和外部 Intent 行为在实现前需要完成 API/OAuth 方案核验，暂标记为【待确认】。

## UI State 设计

所有页面统一使用四类状态：

```text
Loading
Success(data)
Empty
Error(error)
```

建议的语义模型：

- `Loading`：首次加载或用户主动刷新期间没有可展示数据。
- `Success(data)`：数据加载成功且包含可展示内容。
- `Empty`：请求成功，但结果为空；不是网络错误。
- `Error(error)`：请求失败，携带统一领域错误类型和重试能力。

页面可在 State 外独立维护一次性事件：

- 导航到下一个页面。
- 打开 Pinterest 官方页面。
- Toast/Snackbar 等一次性反馈。

刷新时是否保留旧数据并叠加刷新标记为【待确认】；不得把刷新失败误显示为首次加载失败。

## 错误处理

统一错误类型由 Core/Domain 定义，Data 层负责把底层异常映射为领域错误，ViewModel 再映射为用户可理解的 UI 错误。

### API Error

- Pinterest 返回可识别的 API 错误响应。
- 保留可诊断的官方错误信息和请求上下文。
- 不把原始敏感 Token 或完整响应直接展示给用户。
- HTTP 状态和错误字段必须以官方文档核验结果为准。

### OAuth Error

- 用户取消授权。
- 授权回调参数无效。
- Token 交换失败。
- 会话过期或刷新失败。
- 权限不足。

OAuth 错误应允许用户重新登录或返回登录页，不得陷入无限重试。

### Network Error

- 无网络。
- 连接超时。
- DNS/TLS/连接失败。
- 请求取消。

网络错误应提供可重试的 UI 行为；不得把网络错误当作空数据。

### Unknown Error

- 未分类的异常或数据映射失败。
- 记录必要诊断信息，但不泄露 Token、个人数据或完整敏感响应。
- 向用户显示通用错误和重试入口。
- 需要在测试中覆盖异常不会导致应用崩溃。

## 缓存与本地数据边界

- 不离线缓存 Pinterest Boards、Pins、图片或详情数据。
- Android Keystore 负责管理加密密钥。Access Token 和 Refresh Token 使用该密钥进行加密后，保存到本地持久化存储。
- 无网络时展示错误或登录状态，不伪造离线业务数据。

## 当前实现与架构迁移

当前实现仍为 `MainActivity` 集中式 Starter UI，尚未创建上述目标目录和层。

迁移原则：

1. 先完成 Pinterest 官方 OAuth/API 文档核验。
2. 按 v0.2 OAuth 的最小范围引入 Core、Data、Domain 和 Presentation 结构。
3. 每引入一个层，配套更新 API、测试和开发计划文档。
4. 不在架构设计阶段提前实现 Boards、Pins 或 Pin Detail 业务。
