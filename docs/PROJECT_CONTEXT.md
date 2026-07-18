# PinWear 项目产品定义

> 本文档是 PinWear 项目的唯一产品定义文档。
>
> 任何后续开发任务开始前，必须先阅读本文档、`AGENTS.md` 和 `docs/`。除非用户明确更新产品定义，否则不得自行改变本文档中的目标、范围、平台和非目标。

## 项目基本信息

- 项目名称：PinWear
- 项目类型：Wear OS 原生应用
- 目标平台：Wear OS
- 开发语言：Kotlin
- UI 技术：Jetpack Compose for Wear OS
- 设计规范：Material Design 3 Expressive

## 项目目标

PinWear 是一个基于 Pinterest 官方 API 的 Wear OS 客户端，帮助用户在手表上浏览自己的 Pinterest 内容，并在需要时打开 Pinterest 官方页面完成后续操作。

项目必须严格遵守 Pinterest Developer Guidelines。

API 使用边界：

- 仅使用 Pinterest 官方 API。
- 不使用私有 API。
- 不进行 Reverse Engineering。
- 不进行抓包或协议推导。
- 不使用任何非官方接口。

## MVP 目标

第一版 MVP 的核心用户流程：

```text
用户登录 Pinterest
        ↓
查看自己的 Boards
        ↓
查看 Board 中的 Pins
        ↓
查看 Pin 图片
        ↓
查看 Pin 详情
        ↓
打开 Pinterest 官方页面
```

MVP 只覆盖浏览和打开官方页面，不修改 Pinterest 内容。

## MVP 不实现范围

第一版不实现：

- 推荐流
- 首页推荐
- 社交功能
- 评论
- 消息
- 点赞
- 视频
- 广告
- AI 推荐
- 上传图片
- 修改 Pinterest 内容

## API 范围

第一版计划使用以下 Pinterest 官方 API 能力：

- OAuth
- `GET /v5/user_account`
- `GET /v5/boards`
- `GET /v5/boards/{board_id}`
- `GET /v5/boards/{board_id}/pins`
- `GET /v5/pins`
- `GET /v5/pins/{pin_id}`

以下能力不属于 MVP，后续版本再评估：

- Search
- Save Pin

具体 API 的可用字段、权限范围、OAuth 配置、速率限制和审核要求必须以 Pinterest 官方文档及 Developer Guidelines 为准；实现前需要完成官方文档核验。

## 最终目标架构

项目遵循 MVVM，目标数据流如下：

```text
Presentation
      ↓
ViewModel
      ↓
Repository
      ↓
Retrofit / OkHttp
      ↓
Pinterest Official API
```

约束：

- Screen 不得直接访问 API。
- ViewModel 负责 UI 状态和用户事件协调。
- Repository 负责数据来源抽象和数据访问协调。
- Retrofit/OkHttp 负责 HTTP 通信。
- Pinterest API 是唯一远程数据来源。
- 具体模块拆分和接口契约在 OAuth 阶段设计前为【待确认】。

## 计划使用技术

计划使用以下技术：

- Kotlin
- Jetpack Compose
- Compose for Wear OS
- Material 3 Expressive
- Retrofit
- OkHttp
- Kotlin Serialization
- Coil 3
- Paging 3
- DataStore
- Kotlin Coroutines
- Navigation 3

除非用户明确要求，不得新增其他第三方库。上述技术是否全部需要在实际实现中启用，应以对应阶段的最小需求为准，不得提前引入无使用场景的依赖。

## 设计原则

- Round First：优先适配圆形屏幕。
- OLED 优先：优先考虑深色背景、功耗和可读性。
- 图片优先：Pin 图片是核心内容，优先保证加载、裁切和浏览体验。
- 低信息密度：避免在小屏幕上堆叠过多信息。
- 大触控区域：保证手表上的点击目标易于操作。
- 动画遵循 Material Motion。
- 优先官方 Wear OS Design 指导。
- 优先官方 Material Design 3 Expressive 指导。

品牌色、具体 Typography、图片裁切策略、加载占位样式和错误页面细节为【待确认】，实现前应形成 UI 方案。

## 明确不会开发

项目明确不会开发：

- 手机端应用
- 私有 API
- Reverse Engineering
- 抓包
- 离线缓存 Pinterest 数据
- Pinterest 社交功能
- Pinterest 创作功能

## 版本路线

```text
v0.1  Bootstrap
  ↓
v0.2  OAuth
  ↓
v0.3  Boards
  ↓
v0.4  Pins
  ↓
v0.5  Pin Detail
  ↓
v0.6  图片浏览优化
  ↓
v1.0  正式发布
```

版本完成标准需要在各阶段开发前补充到开发计划和测试计划中。

## 产品边界

PinWear 是 Pinterest 内容浏览客户端，不是 Pinterest 内容创作或社交客户端。所有需要修改内容、发布内容或实现社交互动的需求，都超出当前 MVP 和已确认产品范围。

## 待确认事项

- Pinterest OAuth 应用的注册信息、回调方式和权限范围。【待确认】
- Pinterest 官方 API 当前对 Wear OS 客户端、OAuth 流程和请求频率的具体限制。【待确认】
- 官方页面打开方式及手表端外部页面交互边界。【待确认】
- Pin 详情在手表上的字段优先级。【待确认】
- 图片加载失败、超时、限流和无网络时的具体 UI。【待确认】
- v1.0 的正式发布渠道、签名和审核计划。【待确认】

