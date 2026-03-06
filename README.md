## 简化版事件上报系统 Demo（Kotlin / Android）

### 1. 整体架构设计

本 Demo 实现了一个“事件上报”最小闭环：业务侧产生 Event → 非阻塞入队 → 后台消费队列 → 通过多种 Sender 发送（可插拔、可扩展）。

- **Event（数据层）**
  - `Event(name, params, timestamp)`：描述一次用户行为事件。
- **EventReporter（上报事件）**
  - `EventReporter.init(scope, senders)`：初始化，注入协程作用域与发送器列表，可以在MainActivity中控制队列的生命周期。
  - `EventReporter.report(...)`：对外暴露上报 API，调用后立即返回，不阻塞主线程。
- **EventQueue（队列/调度层）**
  - 内部维护事件队列。
  - 使用协程在后台线程消费事件，并按顺序分发给多个 Sender。
- **EventSender（发送策略接口）**
  - `EventSender.sender(event)`：发送行为抽象成接口，支持插拔扩展。
- **内置 Sender**
  - `ConsoleSender`：输出到控制台。
  - `NetworkSender`：模拟网络请求。
  - `UiLogSender`：UI同步展示日志（扩展）。

**非阻塞策略**：UI 线程只负责 `report()` → 入队，真正发送在协程后台执行，避免主线程卡顿。

---

### 2. 如何运行 Demo

#### 环境要求
- Android Studio（任意近期稳定版）
- JDK 17（或 Android Studio 自带 JDK）
- Android SDK（项目 Gradle 配置的 compileSdk / targetSdk 对应版本）
- 最低支持：minSdk 24

#### 运行步骤
1. 获取代码：
   - `git clone <your_repo_url>` 或在 GitHub 页面下载 ZIP 解压。
2. Android Studio 打开项目根目录（包含 `settings.gradle(.kts)` 的那一层）。
3. 等待 Gradle Sync 完成。
4. 连接真机或启动模拟器，点击 Run 运行 `app`。
5. 打开 Logcat，过滤 tag：`EventReporter`，在 App 中触发按钮/操作后可看到：
   - `[Console] ...`（ConsoleSender 输出）
   - `[Network] ...`（NetworkSender 模拟网络输出）
---

### 3. 可继续改进的方向
  - 增加失败重试、退避策略、超时控制
  - 为 NetworkSender 增加失败率模拟与错误分类
  - 增加发送耗时统计、队列长度监控、成功/失败计数
---

### 4. AI 使用说明

本项目使用了 **ChatGPT（GPT-5.2）** 辅助完成。
- 用途：
  - 讨论整体架构（EventReporter / EventQueue / Sender 插拔）
  - 给出 Kotlin 协程、Channel/队列消费的实现建议
  - 帮助梳理 README 的结构与表述
- 我在本地完成了：
  - 代码落地与调试
  - 在 Android Studio 中编译运行验证
