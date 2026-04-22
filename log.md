# 开发日志

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
