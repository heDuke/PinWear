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

- Pinterest 官方 OAuth 的回调、权限和 Token 策略。【待确认】
- Pinterest 官方 API 的字段、分页、限流和审核约束。【待确认】
- Pin 详情、图片加载和官方页面打开的具体 UI 行为。【待确认】
- 工程命名、namespace 和包名是否统一调整。【待确认】
- 正式签名、发布渠道和版本号策略。【待确认】

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
