# 项目上下文

## 基本信息
- Mod Name: DraconicHealingAxe-GTNH
- Mod ID: draconichealingaxe
- Mod Version: 0.2.0-beta
- Package: com.github.skystardust.draconichealingaxe
- Target: Minecraft 1.7.10 + Forge 10.13.4.1614 + GTNH 2.9.0-beta-1 convention
- Build JDK: `JAVA_HOME` 指向 Zulu25；`gradle.properties` 使用 `org.gradle.java.installations.fromEnv = JAVA_HOME`
- 镜像配置：Gradle wrapper 使用 `https://mirrors.cloud.tencent.com/gradle/gradle-9.2.1-bin.zip`；plugin 仓库优先使用阿里云、腾讯云、华为云镜像并保留 GTNH Maven / JitPack 回退。
- Manifest: `elytra.manifest.version = 2.9.0-beta-1`；`dependencies.gradle` 使用 `elytraModpackVersion.gtnh(...)` 解析 GTNH 模组版本。
- GitHub Release: `https://github.com/HOMEFTW/DraconicHealingAxe-GTNH/releases/tag/0.2.0-beta`

## 已实现内容

### 事件处理
| 类 | 事件 | 状态 |
|---|---|---|
| `DraconicHealingEventHandler` | `TickEvent.PlayerTickEvent` | 已实现 |

### 核心逻辑
| 类 | 职责 | 状态 |
|---|---|---|
| `HungerRefillService` | 按 Healing Axe 手持节奏补 hunger 和 saturation | 已实现 |
| `DraconicArmorRegistry` | 通过注册名解析 Draconic Evolution 四件 Draconic 护甲 | 已实现 |
| `AppleCorePlayerHungerState` | 使用 `AppleCoreAPI.mutator` 写入玩家 hunger 和 saturation | 已实现 |
| `NutritionBalanceService` | 将 Nutrition 营养值按步长向 50% 移动 | 已实现 |
| `ReflectiveNutritionAdapter` | 使用反射可选接入 Nutrition 的 `PlayerDataHandler` 和 `NutrientManager` | 已实现 |

### 物品
无。

### 方块
无。

### 材料
无。

### 配方
无。

### 配置项
无。

### Mixins
无。

## 依赖
- Draconic Evolution `1.5.26-GTNH`：运行时必需，用于提供 Draconic 套装。
- AppleCore `3.3.11`：编译与运行时必需，用于安全设置 hunger 和 saturation。
- Nutrition `0.1.9`：运行时可选；存在时启用营养值向 50% 平衡，缺失时静默跳过。
- JUnit Jupiter `5.10.2` 与 JUnit Platform Launcher `1.10.2`：测试依赖。

## 架构说明
- 模组不修改 Draconic Evolution、AppleCore、Extra Utilities 或 Nutrition 源码。
- 服务器端玩家 tick 结束阶段检查护甲栏；任意槽装备 `DraconicEvolution:draconicHelm`、`draconicChest`、`draconicLeggs`、`draconicBoots` 时，每 10 tick 恢复 1 点 hunger 和 1 点 saturation，上限为 20。
- 同一节奏下，如果 Nutrition 已加载，则读取玩家 `NutrientManager` 的所有营养值；低于 50% 的值增加 1 点，高于 50% 的值减少 1 点，距离 50% 小于等于 1 时直接设为 50%。
- Nutrition 适配通过反射实现，`@Mod` 依赖声明使用 `after:nutrition`，避免将 Nutrition 变成硬依赖。

## 验证状态
- `JAVA_HOME=C:\Program Files\Zulu\zulu-25` + `D:\Code\.gtnh-manifests\gradlew-offline.ps1 --gradle-user-home D:\Code\.tools\gradle-home-java25 compileJava`：通过。
- `JAVA_HOME=C:\Program Files\Zulu\zulu-25` + `D:\Code\.gtnh-manifests\gradlew-offline.ps1 --gradle-user-home D:\Code\.tools\gradle-home-java25 build`：通过。

## 可重新生成的构建产物
- `build/libs/draconichealingaxe-0.2.0-beta.jar`
- `build/libs/draconichealingaxe-0.2.0-beta-dev.jar`
- `build/libs/draconichealingaxe-0.2.0-beta-sources.jar`

## 本地工作区
- 已清理 `.gradle/`、`build/`、`run/`、`bin/` 和 `##dummy`；这些内容是本地生成物或临时文件，不作为仓库实现内容保留。
