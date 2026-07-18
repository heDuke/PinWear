# API 技术核验

## 文档状态

本文件记录 PinWear v0.2 Technical Validation 阶段基于 Pinterest 官方开发者文档和官方政策页面核验出的事实。

- 核验日期：2026-07-18
- 远程数据源：仅 Pinterest 官方 API
- 当前状态：尚未实现 OAuth 或 API 客户端
- 不能由本文件推断官方未说明的行为

## 官方文档来源

- [Set up authentication and authorization](https://developers.pinterest.com/docs/getting-started/set-up-authentication-and-authorization/)
- [Pagination](https://developers.pinterest.com/docs/reference/pagination/)
- [Rate limits](https://developer.pinterest.com/docs/reference/rate-limits/)
- [Error codes](https://developers.pinterest.com/docs/reference/error-codes/)
- [Developer Platform Overview](https://developers.pinterest.com/docs/overview/welcome/)
- [Manage user accounts](https://developers.pinterest.com/docs/work-with-organic-content-and-users/manage-user-accounts/)
- [Developer guidelines](https://policy.pinterest.com/en/developer-guidelines)
- [Sandbox](https://developers.pinterest.com/docs/developer-tools/sandbox/)

## OAuth 2.0

### 已确认流程

Pinterest 官方认证文档说明 Authorization Code grant 流程如下：

1. 将用户重定向到 `https://www.pinterest.com/oauth/`。
2. 请求参数包括：
   - `client_id`：App ID。
   - `redirect_uri`：已注册的回调 URI。
   - `response_type=code`。
   - `scope`：至少一个权限，多个权限可用空格或逗号分隔。
   - `state`：可选；官方说明通常用于防止 CSRF，并要求回调值与请求值匹配。
3. 授权成功后，Pinterest 将 `code` 和 `state` 回调到 `redirect_uri`。
4. 客户端向 `POST https://api.pinterest.com/v5/oauth/token` 交换 Token。
5. Token 请求使用 HTTP Basic Authentication，用户名为 `client_id`，密码为 `client_secret`。
6. Authorization Code Token 请求需要 `code`、`redirect_uri` 和 `grant_type=authorization_code`。
7. API 调用使用 `Authorization: Bearer {access_token}`。

### PKCE

在本次核验的 Pinterest 官方认证文档中，没有找到 `PKCE`、`code_challenge` 或 `code_verifier` 的说明。

结论：

- Pinterest 官方文档没有说明 PKCE 是否强制要求。
- Pinterest 官方文档没有说明 PKCE 是否受支持。
- 不能根据通用 OAuth 实践推断 Pinterest 的实现行为。
- 在 Pinterest 官方文档或官方支持明确前，PKCE 状态为【官方未说明】【待确认】。

### 回调 URI

已确认：

- 注册应用时至少提供一个 Redirect URI。
- 授权请求中的 `redirect_uri` 必须与 Pinterest 注册值完全匹配。
- Token 交换请求中的 `redirect_uri` 也必须与注册值完全匹配。
- Pinterest 在授权成功后将 `code` 和 `state` 发送到该 URI。

未确认：

- Wear OS 原生应用应采用哪种具体回调 URI 方案。
- Pinterest 对 Android App Link、Custom Scheme 或其他原生回调形式的支持规则。
- 回调 URI 是否需要公网 HTTPS 地址。

以上均为【官方未说明】【待确认】，不能直接实现。

### Access Token 生命周期

官方认证文档的 Authorization Code 示例响应中：

- `expires_in=2592000` 秒，即 30 天。
- `access_token` 用于 API 请求。
- `token_type` 示例为 `bearer`。
- `scope` 表示实际授予的权限。

实际 Token 的生命周期应以响应中的 `expires_in` 为准，不应硬编码固定过期时间。

### Refresh Token

官方文档确认：

- Refresh Token 只适用于 Authorization Code grants。
- 使用 `POST /v5/oauth/token`。
- 请求需要 `grant_type=refresh_token` 和完整的 `refresh_token`。
- `scope` 可选，用于请求原授权范围的子集。
- 当前官方文档说明：2025-09-25 及之后创建的应用自动使用 continuous refresh token。
- Continuous refresh token 有 60 天有效期，并可持续刷新。
- 刷新成功会返回新的 access token 和 continuous refresh token。
- Refresh Token 过期后，需要重新执行 Authorization Code flow。
- 旧应用的 `continuous_refresh` 参数只适用于 2025-09-25 之前创建的应用。

PinWear 的 Pinterest 应用创建日期尚未确认，因此最终 Token 策略为【待确认】。实现不得默认使用旧版 365 天 legacy refresh token。

### Client Secret 风险

官方 Token 交换文档要求 HTTP Basic Authentication 携带 `client_id:client_secret`。

官方已确认：

- API credentials 必须保持私密。
- Token 不得提交到代码仓库。
- Pinterest 参与 GitHub Secret Scanning，泄露的 Token 可能被撤销。

官方未说明：

- 纯 Wear OS 原生客户端如何安全保存和使用 `client_secret`。
- 是否允许将 client secret 放入 APK。
- 是否必须增加官方认可的中间服务或其他客户端注册模式。

这是一项 OAuth 实现前的阻塞性【待确认】事项。

## OAuth Scope

官方文档说明：

- Scope 是用户授予应用的权限。
- 应按最小权限原则申请 Scope。
- Read Scope 用于读取实体信息。
- Write Scope 用于对实体执行操作。

官方 Scope 表与 MVP 的关系：

| Scope | 官方说明 | MVP 使用情况 |
|---|---|---|
| `user_accounts:read` | 读取用户账户和关注者 | 计划用于 `/v5/user_account` |
| `boards:read` | 读取公开 Boards，包括群组 Boards | MVP 需要 |
| `boards:read_secret` | 读取 secret Boards | 是否纳入“自己的 Boards”待确认 |
| `pins:read` | 读取公开 Pins | MVP 需要 |
| `pins:read_secret` | 读取 secret Pins | 是否纳入自己的 secret Pins 待确认 |

MVP 不需要申请 `boards:write`、`boards:write_secret`、`pins:write` 或 `pins:write_secret`，因为产品明确不修改 Pinterest 内容。

最终 Scope 集合必须根据 MVP 是否包含 secret Boards/Pins 确认，不能过度申请权限。

## User API

官方文档确认存在 `GET /v5/user_account`（Get user account）。官方认证文档使用该接口示例验证 Bearer Token。

官方未在本次核验记录中完整说明 PinWear 所需的全部字段、参数权限组合和字段稳定性；具体 DTO 字段需在 API Reference 逐项核验后再冻结。

## Boards API

官方开发者文档和 Sandbox 端点列表确认 Boards 能力包括：

- `GET /v5/boards`：List boards。
- `GET /v5/boards/{board_id}`：Get board。
- `GET /v5/boards/{board_id}/pins`：List Pins on board。

Boards 的公开/secret 可见性由对应 Scope 控制。具体 query 参数、响应字段、分页支持和错误码必须按每个 API Reference 页面核验；未在本次资料中确认的内容为【官方未说明】。

## Pins API

官方开发者文档和 Sandbox 端点列表确认 Pins 能力包括：

- `GET /v5/pins`：List Pins。
- `GET /v5/pins/{pin_id}`：Get Pin。

Pins 的公开/secret 可见性由 `pins:read` 和 `pins:read_secret` 控制。具体响应字段、图片结构、Pin 类型和每个 endpoint 的分页/权限要求需按 API Reference 核验；未确认内容为【官方未说明】。

## 分页

官方 Pagination 文档确认：

- `page_size` 为整数。
- `page_size` 默认值为 `25`。
- `page_size` 最大值为 `250`。
- `bookmark` 为下一页起点字符串。
- 下一次请求将上一响应的 `bookmark` 作为 query 参数传回。
- 最后一页的 `bookmark` 为 `null`。
- 是否支持分页必须逐个查看具体 endpoint 是否提供 `page_size` 和 `bookmark` 参数。

因此 Board 列表、Board Pins 和 Pins 列表的分页支持不能只依据资源名称推断，必须在实现前逐个核验 API Reference。

## Rate Limit

官方 Rate Limits 文档确认：

- Trial access 的通用限制为所有 API 请求每天 1,000 次。
- Standard access 的通用限制页面显示为每个 user/app 每秒 100 次。
- `org_read` 类别覆盖读取 user accounts、boards、board sections 或 Pins。
- `org_read` 类别表显示 Trial 为每天每 app 1,000 次，Standard 为每分钟每 user/app 1,000 次。
- 官方声明限制可能随时变更。
- 响应中可包含：
  - `x-ratelimit-limit`
  - `x-ratelimit-remaining`
  - `x-ratelimit-reset`

官方页面同时存在通用限制说明和类别限制表，单位展示存在差异；实现应以实际 endpoint 类别和实时响应 header 为准，不硬编码单一数字。

项目不得主动测试或冲击 Pinterest rate limit 或 abuse prevention systems，除非获得 Pinterest 明确授权。

## 错误码

官方 Error Codes 文档说明：

- API 响应中的错误或 warning 具有数字 code 和描述。
- 官方错误码页面不是所有错误的完整列表，很多错误是 endpoint-specific，应查看具体 API Reference。
- 官方认证文档明确说明：access token 过期或无效时返回 HTTP `401`，API error code 为 `2`。
- 官方 FAQ 说明权限不足时需要申请包含正确 Scope 的新 Token。

本项目目前可以确认并映射：

- 无效/过期 Access Token：HTTP 401、API code 2。
- Scope 不足：官方说明需要重新生成包含所需 Scope 的 Token；具体 HTTP 状态和错误 code 为【官方未说明】。
- 其他 API、404、限流、服务端错误的统一 code：必须按具体 endpoint Reference 核验，不能猜测。

## 图片资源访问规则

官方开发者文档确认的产品约束：

- 除账号中的 campaign analytics 外，不得存储通过 Pinterest Materials（包括 API）访问的信息。
- 只能在获得用户授权后访问账号信息。
- 只能使用该账号信息为该用户提供服务。
- 不得将 API 信息与其他用户或其他服务的信息合并。
- 不得分享或出售 API 信息。
- 如果应用发布 Pinterest 内容，必须链接回 Pinterest 上的来源，并清晰说明内容来自 Pinterest。
- 不得遮挡、覆盖或对 Pinterest 内容应用滤镜。

官方未说明：

- Pin API 返回的图片 URL 是否允许长期直连。
- 图片 CDN 的域名、缓存 header、签名有效期和 Referer 要求。
- Coil 3 的内存缓存或系统 HTTP 缓存是否属于政策意义上的“存储”。
- Wear OS 本地临时图片缓存的具体合规边界。

因此 PinWear 只能在完成官方政策/技术核验后设计图片加载缓存策略。当前产品定义中的“不离线缓存 Pinterest 数据”继续有效。

## Pinterest Developer Guidelines 相关限制

与 PinWear 直接相关的官方限制：

- 必须诚实、透明地向 Pinterest 和最终用户说明应用功能。
- 不得收集 Pinterest 用户名、密码或使用登录凭据访问账号。
- 必须通过授权访问用户账号。
- 只能使用用户账号信息为该用户提供服务。
- 不得使用自动化抓取或数据提取，除非 Pinterest 明确允许。
- 不得规避 Pinterest 的政策、区域限制或内容限制。
- 不得未经授权测试 rate limits 或 abuse prevention systems。
- 必须有符合适用法律的隐私政策，申请 API access 时需提供隐私政策链接。
- Pinterest 不允许面向 13 岁以下儿童的应用。
- 不得误导用户或虚假表示与 Pinterest 的关系。

这些规则支持 PinWear 当前“官方 API、授权浏览、无社交/创作/修改、无离线 Pinterest 数据缓存”的产品边界。

## Technical Validation 结论

已确认的接口范围仍为：OAuth、`/v5/user_account`、`/v5/boards`、`/v5/boards/{board_id}`、`/v5/boards/{board_id}/pins`、`/v5/pins`、`/v5/pins/{pin_id}`。

以下内容在实现前仍必须确认：

- PKCE 是否要求或支持。
- 原生 Wear OS 的 Redirect URI 方案。
- client secret 在纯 Wear OS 客户端中的合规安全方案。
- 应用创建日期和对应 refresh token 模式。
- 是否读取 secret Boards/Pins。
- 每个 endpoint 的完整字段、分页、Scope、错误码和 Rate Limit 类别。
- 图片 URL 和临时/内存缓存的官方规则。
