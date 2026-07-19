# Pinterest OAuth External Validation Plan

本文档属于 Sprint 3C 产出物，用于在实际实现 OAuth 流程（Sprint 4）之前，通过 Pinterest Developer Console 验证外部 OAuth 配置、URL 构建规则及跨应用回调能力。

## 1. Pinterest Developer Console 验证

在正式发起 OAuth 请求前，需在 Pinterest 开发者后台确认以下环境配置：

- **当前 App 状态**：需确认应用是处于 Trial Access（试用访问）还是 Standard Access（标准访问）。
- **Trial Access 限制**：如处于 Trial Access，全局 API 限制为每天 1000 次请求。禁止使用自动化测试消耗配额，必须保障足够的配额用于开发调试。
- **OAuth 配置项检查清单**：
  - Client ID (App ID) 是否正确生成。
  - Client Secret 是否可获取。
  - 应用所申请的权限（Scopes）是否已包含后续验证所需的项。
  - 回调地址（Redirect URIs）是否已正确录入并生效。

## 2. Redirect URI Validation

待验证：

- Pinterest 是否允许注册 Custom Scheme（例如 `pinwear://oauth2callback`）。
- Pinterest 是否要求 Redirect URI 必须为 HTTPS。
- 若 Custom Scheme 不被支持，再评估 Android App Links 方案。

目前官方文档没有明确说明上述限制，因此需要通过 Pinterest Developer Console 实际验证。
## 3. Authorization URL Validation

验证向 Pinterest 官方拉起授权页面的标准 URL 参数构建。参数应包括：

- `client_id`: Pinterest 开发者后台获取的 App ID。
- `redirect_uri`: 后台严格匹配的回调地址，且在编码时必须做 URLEncode。
- `response_type`: 固定为 `code`。
- `scope`: 权限列表（需用逗号或空格分隔并 URLEncode）。
- `state`: 客户端生成的防 CSRF 随机字符串，在回调时必须被校验。

**最终 Authorization URL 模板**：
```text
https://www.pinterest.com/oauth/?client_id={CLIENT_ID}&redirect_uri={REDIRECT_URI}&response_type=code&scope=user_accounts:read,boards:read,pins:read&state={STATE}
```

### PKCE (Proof Key for Code Exchange)

Official documentation does not explicitly mention PKCE support.

Status: To Be Verified

## 4. Scope Validation

本阶段及后续 MVP 仅验证及使用以下 Scope：

- **`user_accounts:read`**：用于拉取当前登录用户的账户基础信息，以展示登录状态。
- **`boards:read`**：用于读取用户的公开画板（Boards），在核心业务流中提供画板列表。
- **`pins:read`**：用于获取公开 Pins 列表及其详情，以完成主要的瀑布流浏览体验。

*注：不包含任何 write 权限，符合“仅浏览”的最小权限原则。*

## 5. Callback Validation

验证 Android 系统层至应用架构的 OAuth 授权码回传流转路径：

1. **Browser**: 用户在 Pinterest 页面授权后，浏览器重定向至 Redirect URI (携带 `code` 和 `state`)。
2. **PinWear (OS Intent)**: Android 系统通过 AndroidManifest 中的 `<intent-filter>` 捕获 Deep Link 并唤起/恢复 `MainActivity`。
3. **AuthRepository**: 接收 Intent 传递过来的 URI，解析并校验 `state` 匹配性，随后提取出授权 `code`。
4. **ViewModel**: 监听 Repository 获取到的 `code` 事件，切入 Loading 状态并触发下一阶段的 Token 交换操作。

*(本阶段仅定义设计流，不编写任何 AndroidManifest 注册、代码接收及 ViewModel 实现逻辑。)*

## 6. Token Exchange Plan

定义从 `code` 交换到持久化 Token 的业务规划，未来 Sprint 的具体实现步骤如下：

1. **Authorization Code**: 接收到校验过的 `code`。
2. **POST /v5/oauth/token**: 
   - 携带 Header: `Authorization: Basic {Base64(client_id:client_secret)}`。这是 Pinterest 平台硬性要求 Token Exchange 使用 HTTP Basic Authentication。
   - 携带 Body: `grant_type=authorization_code`, `code={CODE}`, `redirect_uri={REDIRECT_URI}`
3. **Access Token**: 解析响应，将短期 Token 通过 Android Keystore 加密进行本地持久化（用于业务请求头）。
4. **Refresh Token**: 同步持久化 Refresh Token，准备应对后续 HTTP 401 情况下的自动刷新策略。

*注：Pinterest 平台要求在请求中提供 `client_secret`，而将 `client_secret` 保存在纯客户端是本项目的架构决策（ADR-0016），并非 Pinterest 平台要求。本阶段严禁在代码内实现 Token 交换及 `client_secret` 本地化处理。*
