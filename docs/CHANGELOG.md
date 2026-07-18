# 变更日志

## 2026-07-18

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
