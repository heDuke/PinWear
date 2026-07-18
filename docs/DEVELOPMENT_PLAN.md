# 开发计划

## 项目简介

PinWear 是一个 Android Wear OS 应用项目。当前代码基于 Wear OS Compose Starter Sample，应用 ID 为 `com.wear.pin`，但工程名、namespace、Kotlin 包名和部分用户文案仍保留 Starter 相关内容。

产品具体用途、目标用户和核心业务流程为【待确认】。

## 项目目标

当前已确认目标：

- 建立可在 Wear OS 设备运行的 Compose 应用基础。
- 支持圆形 Wear OS 屏幕、不同设备尺寸和字体缩放。
- 建立可持续的构建、测试、发布和文档基础。

PinWear 的最终产品目标为【待确认】。

## 技术栈

- Kotlin 2.4.10
- Android Gradle Plugin 9.4.0-alpha05
- Gradle Wrapper 9.6.1
- Java/Kotlin JVM 17；Gradle daemon toolchain 配置为 21
- Jetpack Compose 与 Compose Compiler
- Compose for Wear OS Material 3 1.6.2
- Navigation 3 与 Wear OS swipe-dismiss 场景策略
- Robolectric、JUnit、Compose UI Test、Roborazzi
- GitHub Actions：Debug 构建/测试/lint，以及 tag 触发的 Release APK 上传

以上版本来自当前仓库配置，未进行版本兼容性判断。

## 架构设计

当前为单模块、以 UI 为中心的 Compose 架构：

```text
app
└── MainActivity
    └── WearApp
        ├── GreetingScreen
        └── ListScreen
            └── SampleDialog
```

当前没有已实现的 ViewModel、Repository、数据层、网络层或持久化层。未来架构应根据确认后的业务需求演进，具体分层为【待确认】。

## 模块划分

当前模块：

- `app`：唯一 Android 应用模块，包含 Activity、Compose UI、主题、资源和单元/截图测试。

当前没有独立的 domain、data、network、wear companion 或移动端模块。

## API 规划

当前没有已确认的远程 API 或本地业务 API。接口规划依赖产品需求、数据来源、认证方式和离线策略，均为【待确认】。

## UI 规划

当前 UI 为模板演示界面：欢迎页、列表页、示例卡片、示例按钮、设置图标按钮、点赞图标按钮和示例 Dialog。

真实页面、导航结构、信息架构、品牌色和交互流程为【待确认】。UI 约束见 [UI_GUIDELINES.md](UI_GUIDELINES.md)。

## 开发阶段

### Phase 0：项目 Bootstrap

- [x] 阅读现有项目、源码、测试和工作流。
- [x] 创建统一 Agent 开发规范。
- [x] 初始化架构、API、UI、测试、决策和变更日志文档。
- [x] 记录当前项目状态、风险和待确认事项。

### Phase 1：产品定义与命名统一

- [ ] 确认 PinWear 的产品定位、目标用户和首个核心流程。
- [ ] 确认工程名、namespace、Kotlin 包名和应用展示名称是否统一调整。
- [ ] 确认首个版本范围、版本号策略和发布签名方案。
- [ ] 将确认结果写入相关文档。

### Phase 2：核心业务设计

- [ ] 确认核心领域模型和状态流。
- [ ] 确认是否需要 ViewModel、Repository、本地存储或远程 API。
- [ ] 完成导航、页面和错误状态设计。
- [ ] 更新架构、API、UI 和测试计划。

### Phase 3：核心功能实现

- [ ] 【待确认】实现首个核心业务流程。
- [ ] 补充加载、空数据、错误和恢复状态。
- [ ] 添加必要的交互测试和截图测试。

### Phase 4：发布准备

- [ ] 配置正式签名和安全的 GitHub Secrets。
- [ ] 验证 Release APK、版本号和 Git tag 流程。
- [ ] 完成 Wear OS 设备兼容性验证。

## 当前阶段

当前处于 **Phase 0：项目 Bootstrap**。没有开始业务功能开发。

## Todo

- [ ] 确认 PinWear 产品需求和首个用户故事。
- [ ] 确认命名统一策略。
- [ ] 确认正式签名和发布策略。
- [ ] 处理或评估现有 Starter 模板残留。
- [ ] 在需求确认后更新架构和 API 规划。

## 已完成

- [x] 建立 `AGENTS.md`。
- [x] 建立 `docs/` 文档集。
- [x] 记录当前单模块 Wear OS Compose 架构。
- [x] 记录现有测试矩阵和 CI/Release 流程。

## 下一阶段

下一阶段是 Phase 1：产品定义与命名统一。开始前需要用户确认产品定位、核心流程、命名策略和版本发布要求。

## 风险

- 工程仍有大量 Compose Starter 命名和示例文案，可能造成产品识别和维护混淆。
- Release 当前使用 debug signing config，不适合正式分发。
- 应用没有业务数据层、ViewModel、Repository 或 API 契约。
- 真实产品需求、用户流程和数据来源尚未确认。
- 当前 Gradle/Android 插件及部分依赖为较新的版本，兼容性需要通过 CI 持续验证。
- CI 已针对 Spotless 与 Gradle Configuration Cache 的问题关闭 CI 中的 Configuration Cache；该策略尚未在本地环境完整复验。

## 待确认事项

- PinWear 的产品定位和目标用户是什么？
- “Pin”代表什么核心对象或业务动作？
- 首个版本必须完成哪些用户流程？
- 是否需要手机端配套应用、账号、网络服务或数据同步？
- 是否需要离线使用和本地持久化？
- 正式发布的签名、渠道和版本号规则是什么？

