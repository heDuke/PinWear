# PinWear Privacy Policy

## 1. 基本信息

PinWear 是基于 Pinterest 官方 API 的第三方 Wear OS 客户端。本应用旨在为智能手表用户提供原生的 Pinterest 浏览体验。

**请注意**：PinWear 是独立开发的第三方应用程序，与 Pinterest, Inc. 没有任何从属、赞助、合作或背书关系。

## 2. 数据访问说明

在您明确授权并登录后，PinWear 可能通过 Pinterest 官方 API 访问您的以下数据：
- Pinterest 用户账户信息
- 您的个人画板 (Boards)
- 您的图钉 (Pins)
- 与上述相关的附加信息

**我们的承诺**：
- **安全认证**：本应用采用标准的 OAuth 2.0 协议进行安全认证，PinWear 不会获取、请求或存储您的 Pinterest 账户密码。
- **范围限制**：仅访问您明确授权 Scope（范围）内的数据。
- **正规途径**：严格使用 Pinterest 官方提供的公开 API 进行数据交互，不使用任何私有或未公开的 API。
- **禁止违规行为**：不对 Pinterest 平台进行任何形式的数据抓取 (Scraping) 或逆向工程。

## 3. 数据使用目的

PinWear 访问您的数据，仅仅是为了在您的 Wear OS 设备上实现基础的浏览和展示功能。具体使用场景严格限制在以下 MVP（最小可行性产品）目标之内：
- 在 Wear OS 上展示您的 Pinterest 内容
- 浏览您的画板 (Boards)
- 浏览画板内的图钉 (Pins)
- 查看具体的 Pin 详情信息

我们不会将您的数据用于任何其他超出上述场景的目的，更不会将您的信息用于个性化广告或跨站追踪。

## 4. 数据存储

为了保护您的数据安全与隐私，PinWear 在架构与实现上遵循极简且安全的原则：
- **认证令牌本地化**：您的 Access Token（访问令牌）和 Refresh Token（刷新令牌）均加密保存在用户当前使用的设备本地。
- **密钥保护**：采用设备底层的 Android Keystore 系统来安全地生成与保护加密密钥。
- **无服务端存储**：PinWear 是一个纯客户端应用，可能会使用临时缓存来优化您的运行体验。我们不长期保存 Pinterest 内容，也不会构建离线 Pinterest 内容库，所有的业务数据均不会被上传或存储到我们或任何第三方的云端服务器。
- **拒绝数据交易**：我们绝不出售、出租或共享您的任何数据。

## 5. Pinterest 内容说明

通过 PinWear 呈现的所有 Pinterest 内容（包括图片、描述、链接等），其版权与所有权均归属于 Pinterest 或原内容创作者及用户本人。
- 我们严格遵守 [Pinterest Developer Guidelines](https://developers.pinterest.com/docs/getting-started/developer-guidelines/) 的所有相关规定。
- 本应用将根据 Pinterest 的要求，在界面中提供原生的来源链接跳转，以便用户在官方渠道中打开。

## 6. 用户删除与撤销

您始终对自己的数据保留完整的控制权：
- **应用内退出**：您可以通过在 PinWear 应用内执行“退出登录 (Logout)”操作，清除设备本地保存的相关认证信息（Token 等）。
- **卸载应用**：从您的 Wear OS 设备上卸载本应用，系统将自动删除应用本地存储的数据，包括您的认证信息。
- **平台撤销授权**：您可以随时登录 Pinterest 官方网站或应用，在“设置” -> “已关联应用 (Connected Apps)”中，找到并撤销对 PinWear 的授权。撤销后，应用将无法再访问您的任何新数据。

## 7. 联系方式

如果您对本隐私政策有任何疑问或需要进一步的帮助，请通过以下方式联系我们：

**联系邮箱**：[contact@example.com](mailto:contact@example.com)
