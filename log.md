# 开发日志

## 2026-06-09：移植到 GTNH 2.9 beta 1

### 已完成
- 将 GTNH 构建插件升级为 `gtnhsettingsconvention 2.0.20`，Gradle wrapper 升级为腾讯云镜像的 `gradle-9.2.1-bin.zip`。
- 在 `gradle.properties` 中声明 `elytra.manifest.version = 2.9.0-beta-1`，并在 `dependencies.gradle` 中使用 `elytraModpackVersion.gtnh(...)` 从 2.9 beta 1 manifest 解析 GTNH 模组依赖。
- 将 `AppleCore` 从硬编码 `3.3.5:dev` 改为 manifest 驱动的 `AppleCore 3.3.11:dev`。
- 为开发运行环境补充 `Draconic-Evolution` 和 `Nutrition` 的 runtime 依赖；`Nutrition` 仍保持反射可选兼容。
- 为 Gradle 9 测试运行补充 `junit-platform-launcher`。
- 将 `.codegraph/` 加入 `.gitignore`，避免提交本地索引。
- 使用 `JAVA_HOME=C:\Program Files\Zulu\zulu-25` 与 `D:\Code\.tools\gradle-home-java25` 验证 `compileJava` 和 `build` 通过。

### 遇到的问题
- **`gtnhgradle 2.0.20` 要求 Java 25**：当前进程默认 `JAVA_HOME` 是 Zulu21，切换到 Zulu25 后继续验证。
- **用户级 Gradle Kotlin init 脚本不兼容 Java 25 版本号解析**：使用临时 Gradle user home `D:\Code\.tools\gradle-home-java25` 绕过 `~/.gradle/init.d/mirrors.gradle.kts`。
- **Gradle 8.14.3 不支持 Java 25 classfile 69**：同步 2.9 项目使用的 Gradle wrapper 到 `9.2.1`。
- **Gradle 9 测试运行器缺少 JUnit Platform launcher**：添加 `org.junit.platform:junit-platform-launcher:1.10.2`。

### 决策
- 不修改 Java 业务逻辑；本次迁移只调整构建、依赖和测试运行配置。
- 使用 manifest 驱动依赖版本，避免后续继续锁定旧 GTNH 模组版本。

---

## 2026-05-08：清理本地生成文件

### 已完成
- 删除本地生成目录 `.gradle/`、`build/`、`run/`、`bin/`。
- 删除 0 字节临时文件 `##dummy`。
- 将 `bin/` 加入 `.gitignore`，避免 IDE/Gradle 编译输出再次进入未跟踪列表。

### 决策
- 只清理可重新生成的本地文件，保留已有未提交配置和项目记录改动。

---

## 2026-04-27：修复项目文档乱码

### 已完成
- 修复 `log.md`、`ToDOLIST.md`、`context.md` 的中文乱码。
- 将三份项目文档重新整理为 UTF-8 无 BOM，并恢复缺失的段落换行。
- 保留当前未提交的腾讯云 Gradle/Maven 镜像配置记录，以及 `JAVA_HOME` 指向 Zulu21 的构建说明。

### 遇到的问题
- **文档被错误编码链路处理**：`ToDOLIST.md` 和 `context.md` 是 UTF-8 中文按 GBK/ANSI 解码后又保存为 UTF-8；`log.md` 还出现了不可逆的 `?` 替换。通过 git 中的干净版本、当前差异和实际配置文件内容补齐。

### 决策
- 只修改三份文档，不触碰代码和其它未提交配置改动。

---

## 2026-04-22：构建入口调整到 Zulu21 环境变量

### 已完成
- 在 `gradle.properties` 中加入 `org.gradle.java.installations.fromEnv = JAVA_HOME`，让 Gradle Java 探测显式跟随环境变量。
- 保持 `gradlew.bat` 的标准行为：wrapper 从 `JAVA_HOME` 启动，因此当前构建入口统一要求 `JAVA_HOME` 指向 Zulu21。
- 切换 Gradle 和 Maven 解析入口到腾讯云镜像。

### 遇到的问题
- 需要通过 `JAVA_HOME` 管理本机 JDK，避免在项目文件中硬编码本机 JDK 路径。

### 决策
- 后续只通过 `JAVA_HOME` 管理构建 JDK。

---

## 2026-04-22：切换 Gradle 和 Maven 到腾讯云镜像

### 已完成
- 将 `gradle/wrapper/gradle-wrapper.properties` 的 `distributionUrl` 切换为 `https://mirrors.cloud.tencent.com/gradle/gradle-8.14.3-bin.zip`。
- 在 `settings.gradle` 的 `pluginManagement.repositories` 中加入 `Tencent Maven Mirror`，并使用 `https://mirrors.cloud.tencent.com/nexus/repository/maven-public/` 作为 Maven 镜像入口。

### 遇到的问题
- 本次只调整项目级 Gradle wrapper 和 plugin/dependency 解析入口，没有创建用户级 `~/.gradle/init.gradle`，避免影响其他 Gradle 项目。

### 决策
- Gradle 分发包使用腾讯云 `/gradle` 镜像；Maven 依赖解析使用腾讯云 `/nexus/repository/maven-public/` 镜像。

---

## 2026-04-22：构建入口迁移到 Zulu25 环境变量

### 已完成
- 在 `gradle.properties` 中加入 `org.gradle.java.installations.fromEnv = JAVA_HOME`，让 Gradle Java 探测显式跟随环境变量。
- 保持 `gradlew.bat` 的标准行为：wrapper 从 `JAVA_HOME` 启动，因此当前构建入口统一要求 `JAVA_HOME` 指向 Zulu25。
- 验证 `.\gradlew.bat --no-daemon --version`，确认 `Launcher JVM: 25.0.2`，`Daemon JVM: C:\Program Files\Zulu\zulu-25`。

### 遇到的问题
- 当前 Codex 沙箱进程仍继承旧的进程级 `JAVA_HOME=C:\Program Files\Zulu\zulu-21`，但用户级环境变量已是 Zulu25；验证时刷新到用户级 `JAVA_HOME` 后通过。

### 决策
- 不在仓库中硬编码本机 JDK 路径，后续只通过 `JAVA_HOME` 管理构建 JDK。

---

## 2026-04-22：加入 Nutrition 50% 平衡

### 已完成
- 添加 `NutritionBalanceService`，将所有营养值按每次 1 点向 50% 移动。
- 添加 `ReflectiveNutritionAdapter`，在 Nutrition 已加载时通过 `PlayerDataHandler` 和 `NutrientManager` 同步玩家营养值。
- 将 `DraconicHealingEventHandler` 扩展为同一 Healing Axe 补给节奏下同时执行 hunger/saturation 恢复和 Nutrition 平衡。
- 将版本提升到 `0.1.1` 并生成新 jar。
- 验证 `.\gradlew.bat test --tests com.github.skystardust.draconichealingaxe.nutrition.NutritionBalanceServiceTest` 和 `.\gradlew.bat build` 均通过。

### 遇到的问题
- **工具进程仍继承旧 `JAVA_HOME=C:\Program Files\Zulu\zulu-21`**：用户已卸载 zulu21，验证命令临时使用 `C:\PROGRA~1\Zulu\zulu-25` 和 `GRADLE_OPTS=-Dorg.gradle.java.home=C:\PROGRA~1\Zulu\zulu-25`。
- **首次 `build` 失败在 `spotlessJavaCheck`**：运行 `.\gradlew.bat spotlessApply` 后格式检查通过。

### 决策
- Nutrition 作为可选兼容处理，使用 `after:nutrition` 和反射适配，不增加硬依赖。
- 营养值不会一次性设为 50%，而是按 Healing Axe 补给节奏平滑拉向 50%，避免过强或突兀。

---

## 2026-04-22：创建 DraconicHealingAxe-GTNH

### 已完成
- 搭建独立 GTNH 1.7.10 模组骨架。
- 添加 Draconic 套装任意单件触发 Healing Axe 手持补饥饿/饱和效果的核心实现。
- 添加 `HungerRefillService` 单元测试和基础元数据测试。
- 验证 `.\gradlew.bat test`、`.\gradlew.bat compileJava` 和 `.\gradlew.bat build` 均通过。
- 生成 `build/libs/draconichealingaxe-0.1.0.jar`、`build/libs/draconichealingaxe-0.1.0-dev.jar` 和 sources jar。

### 遇到的问题
- **Healing Axe 源码不在本地源码树中**：通过 GTNH Wiki 和物品注册名资料确认其来自 `ExtraUtilities:defoliageAxe`，本模组只复刻手持补饥饿/饱和行为。
- **首次 `build` 失败在 `spotlessJavaCheck`**：根因是新建 Java 文件未套用 GTNH Spotless 格式；运行 `.\gradlew.bat spotlessApply` 后完整构建通过。

### 决策
- 使用 Draconic Evolution 四件护甲的注册名检测，不直接依赖其 Java 类，降低编译耦合。
- 使用 AppleCore `AppleCoreAPI.mutator` 写入 hunger 和 saturation，避免反射修改 `FoodStats` 私有字段。
- 当前仅实现手持 Healing Axe 的补给行为，不处理攻击生物相关效果。

---
