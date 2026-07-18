# 变更日志

## 2026-07-18

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
