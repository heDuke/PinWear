# 技术决策记录

## ADR-0001：Bootstrap 阶段保持现有单模块架构

- 日期：2026-07-18
- 状态：已确认（基于当前代码事实）
- 决策：本阶段不新增模块、不引入 ViewModel、Repository、网络层或持久化层。
- 背景：当前仓库只有一个 `app` 模块，代码是 UI Starter 样例，真实业务需求尚未确认。
- 影响：保持改动最小；后续业务需求明确后再评估分层和模块化。

## ADR-0002：保持 Wear OS Compose Material 3 技术路线

- 日期：2026-07-18
- 状态：已确认（基于当前配置）
- 决策：后续 UI 开发优先复用当前 Compose for Wear OS Material 3、Wear Navigation 3 和 Wear Foundation。
- 背景：现有页面和测试已建立在该技术栈上。
- 影响：后续组件和交互设计应遵循 Wear OS 组件及圆形屏幕适配原则。

## ADR-0003：CI 中关闭 Gradle Configuration Cache

- 日期：2026-07-18
- 状态：已实施
- 决策：GitHub Actions 的构建命令显式传入 `-Dorg.gradle.configuration-cache=false`。
- 背景：CI 报告显示 Spotless 的 `ConfigurationCacheHackList` 无法写入 Configuration Cache。
- 影响：CI 优先保证构建稳定；本地 `gradle.properties` 仍保留原有配置。该策略后续可在依赖兼容后重新评估。

## ADR-0004：Release 通过 tag 上传 APK

- 日期：2026-07-18
- 状态：已实施但发布安全性待完善
- 决策：推送 `v*` tag 时，GitHub Actions 构建 Release APK 并使用 GitHub CLI 创建 Release。
- 背景：项目已存在 `.github/workflows/release.yml`。
- 影响：发布流程简单明确；当前 Release 使用 debug signing config，正式发布前必须配置正式签名。

## 待决策

- Pinterest OAuth 应用注册、回调方式和权限范围。【待确认】
- Pinterest 官方 API 的字段、分页、限流和审核约束。【待确认】
- Pin 详情、图片加载和官方页面打开的具体 UI 行为。【待确认】
- 工程命名、namespace 和包名是否统一调整。【待确认】
- 正式签名、发布渠道和版本号策略。【待确认】

## ADR-0016：纯 Wear OS 客户端存储 client_secret

- 日期：2026-07-18
- 状态：已决策
- 决策：第一版采用纯 Wear OS 客户端方案，将 client_secret 和 OAuth Token 保存在本地客户端，接受 client_secret 无法绝对保护的风险。不再讨论 Backend OAuth Gateway 或手机代理方案。
- 背景：为了降低架构复杂度，并在无需后端中转的前提下满足用户对原生的诉求。
- 影响：客户端需尽最大努力安全存储（如使用 DataStore 等），不再将其作为业务开发阻塞项。图片 CDN/缓存也降级为开放问题。

## ADR-0005：产品范围限定为 Pinterest 官方 API 浏览客户端

- 日期：2026-07-18
- 状态：已确认
- 决策：PinWear 面向 Wear OS，MVP 仅支持 Pinterest 登录、Boards、Pins、Pin 图片、Pin 详情和打开官方页面。
- 背景：产品定义已明确不实现推荐、社交、创作和内容修改能力。
- 影响：所有超出浏览范围的需求必须单独确认，不得默认加入 MVP。

## ADR-0006：仅使用 Pinterest 官方 API

- 日期：2026-07-18
- 状态：已确认
- 决策：禁止使用私有 API、Reverse Engineering、抓包和非官方接口，并遵守 Pinterest Developer Guidelines。
- 背景：这是产品定义中的硬性边界。
- 影响：API 能力、权限和页面行为必须以 Pinterest 官方文档可验证内容为依据。

## ADR-0007：目标架构采用 MVVM

- 日期：2026-07-18
- 状态：已确认目标
- 决策：目标数据流为 Presentation → ViewModel → Repository → Retrofit/OkHttp → Pinterest API，Screen 不得直接访问 API。
- 背景：需要隔离 Wear OS UI 与远程数据访问。
- 影响：当前 Starter UI 不立即重构；在 v0.2 OAuth 阶段按最小需求逐步引入。

## ADR-0008：保持单 app 模块，采用包级分层

- 日期：2026-07-18
- 状态：已确认目标架构
- 决策：不新增独立 Gradle 模块；在单 `app` 模块内划分 `core`、`data`、`domain` 和 `presentation` 包。
- 背景：当前仓库只有一个 Wear OS app 模块，产品暂不包含手机端应用。
- 影响：降低模块化初期复杂度，同时通过依赖方向保持边界；未来新增 Gradle 模块需要单独评审。

## ADR-0009：Repository 隔离远程数据源

- 日期：2026-07-18
- 状态：已确认目标架构
- 决策：Repository 接口位于 Domain，具体实现位于 Data；Remote Data Source 只负责 Pinterest 官方 API 通信。
- 背景：Screen 和 ViewModel 不应直接依赖 Retrofit、OkHttp 或 DTO。
- 影响：UI 与 API 契约解耦，便于测试和官方 API 变更时的映射调整。

## ADR-0010：统一 UI State 和错误边界

- 日期：2026-07-18
- 状态：已确认目标架构
- 决策：页面统一处理 Loading、Success、Empty、Error；Data 层将 API、OAuth、网络和未知异常映射为领域错误。
- 背景：Wear OS 小屏幕需要明确、可恢复的状态反馈，且不能把网络错误显示为空数据。
- 影响：ViewModel 对外暴露稳定状态，Screen 不处理 HTTP 异常和 DTO。

## ADR-0011：不缓存 Pinterest 业务内容

- 日期：2026-07-18
- 状态：已确认产品边界
- 决策：不离线缓存 Boards、Pins、图片或详情；本地数据仅用于 OAuth 会话所需信息。
- 背景：产品定义明确禁止离线缓存 Pinterest 数据。
- 影响：必须重点处理无网络、超时、限流和重新加载体验；Token 的具体安全存储方案仍需 OAuth 阶段确认。

## ADR-0012：OAuth 是 v0.2 的实现前置门槛

- 日期：2026-07-18
- 状态：已确认流程
- 决策：先核验 Pinterest 官方 OAuth 文档、权限、回调和 Developer Guidelines，再开始 Kotlin 实现。
- 背景：项目禁止猜测官方 API 行为，也禁止使用非官方接口或逆向信息。
- 影响：当前 Architecture Design 已完成，但 v0.2 的代码实现必须等待官方契约确认。

## ADR-0013：Technical Validation 只采纳 Pinterest 官方资料

- 日期：2026-07-18
- 状态：已确认
- 决策：OAuth、API、限流、错误、图片资源和合规结论只采纳 Pinterest 官方开发者文档或官方政策页面；官方未说明的内容不得推断。
- 背景：项目明确禁止私有 API、Reverse Engineering、抓包和非官方接口。
- 影响：PKCE、原生回调、client secret 和图片缓存等问题必须在官方资料或官方支持确认后才能实现。

## ADR-0014：MVP 采用最小读取权限

- 日期：2026-07-18
- 状态：已确认目标
- 决策：MVP 默认只申请读取用户账户、公开 Boards 和公开 Pins 所需权限；secret Boards/Pins 只有在产品范围确认后才申请对应 Scope。
- 背景：Pinterest 官方建议遵循最小 Scope 原则，MVP 不修改 Pinterest 内容。
- 影响：不申请 write Scope；“自己的 Boards”是否包含 secret Boards 仍需确认。

## ADR-0015：不把官方未说明内容编码为既定行为

- 日期：2026-07-18
- 状态：已确认
- 决策：没有官方依据的 PKCE、Redirect URI 原生方案、client secret 处理、图片 CDN 和缓存行为统一标记为【官方未说明】【待确认】。
- 背景：Pinterest 官方认证文档未出现 PKCE/code_challenge，图片访问政策也未定义所有客户端缓存细节。
- 影响：Technical Validation 未闭环前不开始 OAuth Kotlin 实现。
