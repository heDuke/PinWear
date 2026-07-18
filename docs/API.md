# API 设计

## 当前状态

产品定义已确认 PinWear 仅使用 Pinterest 官方 API，目标架构为：

```text
Presentation → ViewModel → Repository → Retrofit/OkHttp → Pinterest Official API
```

当前代码尚未实现远程 API、OAuth、Repository、ViewModel 或网络客户端。以下为计划接口，不代表已经接入。

## 官方 API 边界

- 仅允许使用 Pinterest 官方 API。
- 必须遵守 Pinterest Developer Guidelines。
- 禁止私有 API、Reverse Engineering、抓包和非官方接口。
- 具体权限、字段、版本、限流和审核要求以 Pinterest 官方文档为准。

## MVP 计划接口

- OAuth：Pinterest 用户登录和授权。
- `GET /v5/user_account`：获取当前用户账号信息。
- `GET /v5/boards`：获取用户 Boards。
- `GET /v5/boards/{board_id}`：获取单个 Board。
- `GET /v5/boards/{board_id}/pins`：获取 Board 中的 Pins。
- `GET /v5/pins`：MVP 计划使用的 Pins 查询接口，具体调用场景为【待确认】。
- `GET /v5/pins/{pin_id}`：获取单个 Pin 详情。

后续版本再评估：

- Search
- Save Pin

## 规划原则

如果后续引入 API，必须先确认并记录：

- API 的用途和用户流程必须符合 `PROJECT_CONTEXT.md` 的 MVP 范围。
- Base URL 和环境划分。
- HTTP 方法、路径和请求参数。
- 请求及响应数据模型。
- 认证、授权和敏感数据处理。
- 超时、重试、取消和错误映射。
- 离线策略、缓存和数据一致性。
- 版本兼容和迁移策略。

## 当前本地接口

当前仅有 UI 事件回调：

- `GreetingScreen.onShowList`：请求进入列表页面。
- `SampleDialog.onDismiss`：请求关闭 Dialog。
- `SampleDialog.onCancel`：处理取消动作的回调入口，当前由调用方传入空实现。
- `SampleDialog.onOk`：处理确认动作的回调入口，当前由调用方传入空实现。

这些不是稳定的业务 API，后续业务化前需要重新确认命名和职责。

## 待确认事项

- Pinterest OAuth 的回调方式、权限范围和 Token 生命周期。【待确认】
- 官方 API 的字段、分页、限流、错误和审核约束。【待确认】
- `GET /v5/pins` 在 MVP 中的具体使用场景。【待确认】
- 打开 Pinterest 官方页面的链接格式和 Wear OS 行为。【待确认】
