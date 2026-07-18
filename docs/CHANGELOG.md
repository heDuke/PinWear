# 变更日志

## 2026-07-18

### Sprint 1.5: Project Cleanup

- 将包名从 `com.example.android.wearable.composestarter` 成功迁移至 `com.wear.pin`。
- 清理所有 DTO 中的推测字段，添加了待官方 API Reference 核验的 TODO 标记，保证 DTO 与 Domain Model 解耦且不包含未经官方确认的字段。
- 梳理并列出了 Starter 遗留代码（MainActivity 内的 Greeting、ListScreen 示例、字符串资源及对应单元测试），作为后续清理计划保留。

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
