# PinWear

PinWear 是基于 Pinterest 官方 API 的第三方 Wear OS 客户端。

本项目严格使用 Pinterest 官方 API 进行数据访问，并遵守 [Pinterest Developer Guidelines](https://developers.pinterest.com/docs/getting-started/developer-guidelines/) 的相关要求。

**注意**：本项目目前正处于早期开发阶段。

## 核心目标

MVP 目标包括：
- **Wear OS 原生体验**：使用 Compose for Wear OS 与 Material 3 打造适配圆形手表的现代交互。
- **浏览个人画板 (Boards)**：支持查看当前登录用户自己的画板列表。
- **浏览图钉 (Pins)**：支持查看画板内的 Pin 列表。
- **查看 Pin 详情**：支持在手表上查看具体的 Pin 图片和描述信息。
- **打开官方页面**：支持从手表触发在手机端或浏览器中打开 Pinterest 官方页面。

## 技术栈

- Kotlin
- Jetpack Compose for Wear OS
- Material 3
- Retrofit (网络层)
- Kotlin Serialization (数据序列化)
- Coroutines (异步与流控制)

## 架构概览

本项目采用典型的 MVVM 分层架构，保持单向数据流与清晰的职责边界：

```
Presentation 层 (Compose UI)
      ↓
 ViewModel (状态管理与事件处理)
      ↓
Repository 层 (数据契约与业务逻辑)
      ↓
Remote Data Source (远程数据源包装)
      ↓
 Pinterest API (Retrofit 接口)
```

## 当前开发状态

- [x] Bootstrap 完成 (Phase 0)
- [x] Architecture Design 完成
- [x] Networking Foundation 完成 (Sprint 2)
- [x] Pinterest API Definition 完成 (Sprint 2.5)
- [ ] OAuth Technical Validation 进行中 (Sprint 3)

详细的开发计划请参阅 [DEVELOPMENT_PLAN.md](docs/DEVELOPMENT_PLAN.md)。

## 开发规范

本项目的开发严格遵循以下内部文档：
- **[AGENTS.md](AGENTS.md)**: AI Code Agent 与多协作者必须遵守的基础开发规则。
- **[ARCHITECTURE.md](docs/ARCHITECTURE.md)**: 架构设计约束与层次说明。
- **[PROJECT_CONTEXT.md](docs/PROJECT_CONTEXT.md)**: 产品的业务范围定义和功能规划。

## Build (构建方式)

本项目使用 Gradle Wrapper 进行构建，兼容 Windows, macOS, 与 Linux 环境。

```bash
# Debug 构建
./gradlew build lintDebug

# 清理构建
./gradlew clean
```
