# 变更日志

## 2026-07-18

### Sprint 3A: Authentication Foundation

- 创建了认证域抽象层：`AuthState` 模型和 `AuthRepository` 接口。
- 设计并创建了本地安全存储接口 `TokenStorage`，为后续 Token 加密存储做准备。
- 实现 OAuth 工具类：构建 Pinterest 授权 URL 的 `OAuthUrlBuilder` 和生成防跨站伪造随机状态的 `OAuthStateGenerator`（已替换掉对 Android framework/Robolectric 依赖）。
- 实现了用于 UI 开发和测试的 `FakeAuthRepository` 占位，利用 `MutableStateFlow` 模拟了带延迟的登录/注销状态流，不直接调用真实 Pinterest API。
- 添加并跑通了上述组件的单元测试（不再依赖 Robolectric）。

### Sprint 2.5: Pinterest API Definition

- 依据官方分页结构，新增了 `PageDto` 泛型包装（包含 `items` 列表和 `bookmark` 分页标记），其余字段保留 TODO 等待核实。
- 在 `PinterestApi.kt` 中声明了 6 个基础 Endpoint（用户、画板列表、画板详情、画板 Pin 列表、Pin 列表、Pin 详情），并附带对应官方文档的 `@see` 链接。
- 所有 Query 参数（`bookmark`、`page_size`）均严格遵照官方 API Reference。
- 遵循架构边界，不涉及任何 Auth 或业务实现。


### Sprint 2: Networking Foundation

- 引入 Retrofit (3.0.0)、OkHttp BOM (4.12.0) 及 Kotlinx Serialization Converter 依赖。
- 创建领域级网络异常基类 `NetworkException` 及子类，放置于 `core/network/exception` 包。
- 创建拆解后的网络核心组件 `JsonProvider`、`OkHttpProvider` 和 `RetrofitProvider`。
- 配置了包含忽略未知字段等的安全 Json 解析策略，及 Debug 模式下的 `HttpLoggingInterceptor`。
- 创建空的 `PinterestApi` 接口存根。


### Sprint 1.5: Project Cleanup

- 将包名从 `com.example.android.wearable.composestarter` 成功迁移至 `com.wear.pin`。
- 清理所有 DTO 中的推测字段，添加了待官方 API Reference 核验的 TODO 标记，保证 DTO 与 Domain Model 解耦且不包含未经官方确认的字段。
- 梳理并列出了 Starter 遗留代码（MainActivity 内的 Greeting、ListScreen 示例、字符串资源及对应单元测试），作为后续清理计划保留。
- 替换 Compose Starter 默认应用图标为 PinWear 临时 Alpha 图标，删除了无用的 drawable 向量图背景/前景，仅使用 mipmap png，不影响 Manifest 结构。
- 移除原 Compose Starter Sample 的 README 内容，重写并迁移为专属于 PinWear 的项目介绍文档。

### Sprint 1: Project Skeleton

- 建立项目的 `core`、`data`、`domain`、`presentation` 基础包结构。
- 建立 `OAuthToken`、`UserAccount`、`Board`、`Pin` 的 Domain Model。
- 建立 `AuthRepository`、`UserRepository`、`BoardRepository`、`PinRepository` 接口定义。
- 建立上述模型的网络 DTO 数据类及 Mapper 文件存根。
- 保证无实际业务逻辑，完全遵循 Architecture Freeze 的范围限制。

### Technical Validation

- 基于 Pinterest 官方开发者文档和官方 Developer Guidelines 核验 OAuth、Scope、Token、分页、Rate Limit、错误码和图片/内容政策。
- 确认 Authorization Code flow、Redirect URI 精确匹配、最小 Scope、Access Token 和 continuous Refresh Token 机制。
- 确认 `user_account`、Boards、Pins API 的计划范围及 bookmark 分页机制。
- 记录官方未说明的 PKCE、Wear OS 原生回调、client secret 和图片资源访问问题。
- 确认 v0.2 OAuth 实现必须等待阻塞性技术问题完成官方确认。

### Architecture Design

- 冻结 Presentation → ViewModel → Repository → Remote Data Source → Pinterest API 架构。
- 规划单 `app` 模块内的 `core`、`data`、`domain` 和 `presentation` 包结构。
- 规划 DTO、Domain Model、UI Model 及数据转换边界。
- 规划 Auth、User、Board、Pin Repository 和五类 ViewModel。
- 规划 Splash、Login、Home、Board、Pin Detail 导航流程。
- 统一 Loading、Success、Empty、Error 状态及 API/OAuth/Network/Unknown 错误处理。
- 记录单模块、Repository 隔离、无 Pinterest 业务缓存和 OAuth 前置核验等架构决策。
- 明确纯 Wear OS 客户端方案，由客户端直接调用接口交换 Token，不存在任何后端中间件。
- 确认 Android Keystore 仅负责管理加密密钥，Access Token 和 Refresh Token 经加密后保存到本地持久化存储。
- 制定 Token 并发刷新策略：全局收敛单一 Refresh 请求。
- 细化 Refresh 异常处理：网络错误保留当前状态等待重试，认证错误则统一退出登录（清除缓存并返回 Login）。

### Product Definition

- 确认 PinWear 为 Pinterest 官方 API 的 Wear OS 客户端。
- 确认 MVP 流程：OAuth、Boards、Board Pins、Pin 图片、Pin 详情和打开官方页面。
- 确认 Pinterest Developer Guidelines 及仅使用官方 API 的硬性边界。
- 确认 MVVM 目标架构、Wear OS 设计原则和 v0.1 到 v1.0 版本路线。
- 新增 `docs/PROJECT_CONTEXT.md` 作为唯一产品定义文档。
- 同步更新开发计划、API、架构、UI 和技术决策文档。

### Bootstrap

- 完成项目文件、源码、资源、测试和 GitHub Actions 工作流盘点。
- 创建统一的 `AGENTS.md` 开发规范。
- 创建项目架构、API、UI、测试、决策和开发计划文档。
- 确认当前项目处于 Phase 0：项目 Bootstrap，尚未开始业务功能开发。

## 历史记录

当前仓库没有已有 `CHANGELOG.md`。历史提交信息未提供足够的产品变更说明，因此不在此处臆测补录。
